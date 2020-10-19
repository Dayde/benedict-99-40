package fr.destiny.benedict.web.service;

import com.google.common.collect.ImmutableMap;
import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import fr.destiny.benedict.web.model.*;
import fr.destiny.benedict.web.repository.DestinyInventoryItemRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static fr.destiny.benedict.web.utils.Utils.mergeMaps;


@Service
public class ItemService {

    private static final String VAULT = "";

    private Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions;

    private Destiny2Api destiny2Api;

    ItemService(@Autowired @Qualifier("destiny2ApiScoped") Destiny2Api destiny2Api, @Autowired DestinyInventoryItemRepository itemRepository) {
        this.destiny2Api = destiny2Api;
        this.itemDefinitions = ImmutableMap.copyOf(
                itemRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(DestinyDefinitionsDestinyInventoryItemDefinition::getHash,
                                item -> item))
        );
    }

    public Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> getItemDefinitions() {
        return itemDefinitions;
    }

    public Set<ItemInstance> getItemInstances(
            Long membershipId,
            Integer membershipType,
            ClassType classType,
            ItemCategory itemCategory,
            String token) {

        if (!StringUtils.isEmpty(token)) {
            destiny2Api.getApiClient().addDefaultHeader("Authorization", "Bearer " + token);
        }

        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType);

        Map<Long, Set<Pair<String, Long>>> instanceIdsByItemHash = new HashMap<>();

        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getEquippedItems(profile));
        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getCharacterInventoryItems(profile));
        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getVaultItems(profile));

        Map<String, DestinyEntitiesItemsDestinyItemInstanceComponent> instances = profile.getItemComponents().getInstances().getData();
        if (instances == null) {
            instances = Collections.emptyMap();
        }
        Map<String, DestinyEntitiesItemsDestinyItemSocketsComponent> sockets = profile.getItemComponents().getSockets().getData();
        if (sockets == null) {
            sockets = Collections.emptyMap();
        }
        Map<String, DestinyEntitiesItemsDestinyItemStatsComponent> stats = profile.getItemComponents().getStats().getData();
        if (stats == null) {
            stats = Collections.emptyMap();
        }

        return generateItemInstances(instanceIdsByItemHash, instances, sockets, stats, classType, itemCategory);
    }

    private Set<ItemInstance> generateItemInstances(
            Map<Long, Set<Pair<String, Long>>> instanceIdsByItemHash,
            Map<String, DestinyEntitiesItemsDestinyItemInstanceComponent> instances,
            Map<String, DestinyEntitiesItemsDestinyItemSocketsComponent> sockets,
            Map<String, DestinyEntitiesItemsDestinyItemStatsComponent> stats,
            ClassType classType, ItemCategory itemCategory) {
        Set<ItemInstance> itemInstances = new HashSet<>();
        instanceIdsByItemHash.forEach((itemHash, instanceIds) -> {
            DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition = itemDefinitions.get(itemHash);
            ClassType itemClassType = ClassType.fromHash(itemDefinition.getClassType());

            if (classType != ClassType.ANY
                    && itemClassType != ClassType.ANY
                    && classType != itemClassType) {
                return;
            }

            if (itemDefinition.getItemCategoryHashes() == null) {
                return;
            }

            if (!itemDefinition.getItemCategoryHashes().contains(itemCategory.getHash())) {
                return;
            }

            instanceIds.forEach(locationInstanceIdPair ->
                    {
                        String location = locationInstanceIdPair.getLeft();
                        long instanceId = locationInstanceIdPair.getRight();
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent = sockets.get(Long.toString(instanceId));
                        if (socketsComponent == null) {
                            socketsComponent =
                                    new DestinyEntitiesItemsDestinyItemSocketsComponent();
                            socketsComponent.setSockets(Collections.emptyList());
                        }
                        DestinyEntitiesItemsDestinyItemStatsComponent statsComponent = stats.get(Long.toString(instanceId));
                        if (statsComponent == null) {
                            statsComponent =
                                    new DestinyEntitiesItemsDestinyItemStatsComponent();
                            statsComponent.setStats(Collections.emptyMap());
                        }

                        ItemInstance item = new ItemInstance(
                                instanceId,
                                instances.get(Long.toString(instanceId)),
                                socketsComponent,
                                statsComponent,
                                itemDefinition,
                                location
                        );
                        if (item.getEnergyType() != EnergyEnum.NONE) {
                            itemInstances.add(item);
                        }
                    }
            );
        });

        return itemInstances;
    }

    private Map<Long, Set<Pair<String, Long>>> getVaultItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Pair<String, Long>>> itemInstanceIds = new HashMap<>();

        DestinyEntitiesInventoryDestinyInventoryComponent data = profile.getProfileInventory().getData();
        if (data != null) {
            itemInstanceIds = mergeMaps(itemInstanceIds, collectInstanceIdsByItemHash(VAULT, data));
        }

        return itemInstanceIds;
    }

    private Map<Long, Set<Pair<String, Long>>> getCharacterInventoryItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Pair<String, Long>>> itemInstanceIds = new HashMap<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterInventories().getData();
        if (datas != null) {
            for (Map.Entry<String, DestinyEntitiesInventoryDestinyInventoryComponent> entry : datas.entrySet()) {
                itemInstanceIds = mergeMaps(itemInstanceIds, collectInstanceIdsByItemHash(entry.getKey(), entry.getValue()));
            }
        }

        return itemInstanceIds;
    }

    private Map<Long, Set<Pair<String, Long>>> getEquippedItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Pair<String, Long>>> itemInstanceIds = new HashMap<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterEquipment().getData();
        if (datas != null) {
            for (Map.Entry<String, DestinyEntitiesInventoryDestinyInventoryComponent> entry : datas.entrySet()) {
                itemInstanceIds = mergeMaps(itemInstanceIds, collectInstanceIdsByItemHash(entry.getKey(), entry.getValue()));

            }
        }

        return itemInstanceIds;
    }


    private DestinyResponsesDestinyProfileResponse getProfile(Long membershipId, Integer membershipType) {
        List<DestinyDestinyComponentType> requiredComponents = Arrays.asList(
                // Vault
                DestinyDestinyComponentType.NUMBER_102,
                // Character Inventories
                DestinyDestinyComponentType.NUMBER_201,
                // Character equipment
                DestinyDestinyComponentType.NUMBER_205,
                // Item Instances
                DestinyDestinyComponentType.NUMBER_300,
                // Item stats
                DestinyDestinyComponentType.NUMBER_304,
                // Item Sockets
                DestinyDestinyComponentType.NUMBER_305
        );
        return destiny2Api
                .destiny2GetProfile(
                        membershipId,
                        membershipType,
                        requiredComponents.stream()
                                .map(DestinyDestinyComponentType::getValue)
                                .collect(Collectors.toList())
                ).getResponse();
    }

    private Map<Long, Set<Pair<String, Long>>> collectInstanceIdsByItemHash(String location, DestinyEntitiesInventoryDestinyInventoryComponent data) {
        return data
                .getItems()
                .stream()
                .filter(item -> GearBucketHash.fromHash(item.getBucketHash()) != null)
                .collect(Collectors.groupingBy(DestinyEntitiesItemsDestinyItemComponent::getItemHash))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue()
                                        .stream()
                                        .map(instance -> Pair.of(location, instance.getItemInstanceId()))
                                        .collect(Collectors.toSet())
                        )
                );
    }

    public String transferItem(String token, int platform, long itemHash, long instanceId, String from, String to) {
        if (from.equals(to)) {
            return "Ok";
        }

        destiny2Api.getApiClient().addDefaultHeader("Authorization", "Bearer " + token);


        if (VAULT.equals(from) || VAULT.equals(to)) {
            return transfertItemFromTo(platform, itemHash, instanceId, from, to);
        } else {
            transfertItemFromTo(platform, itemHash, instanceId, from, VAULT);
            return transfertItemFromTo(platform, itemHash, instanceId, VAULT, to);
        }
    }

    private String transfertItemFromTo(int platform, long itemHash, long instanceId, String from, String to) {
        DestinyRequestsDestinyItemTransferRequest transferRequest = new DestinyRequestsDestinyItemTransferRequest();
        transferRequest.setTransferToVault(VAULT.equals(to));
        transferRequest.setMembershipType(platform);
        transferRequest.setItemReferenceHash(itemHash);
        transferRequest.setItemId(instanceId);
        transferRequest.setCharacterId(VAULT.equals(to) ? Long.parseLong(from) : Long.parseLong(to));
        return destiny2Api.destiny2TransferItem(transferRequest).getMessage();
    }
}
