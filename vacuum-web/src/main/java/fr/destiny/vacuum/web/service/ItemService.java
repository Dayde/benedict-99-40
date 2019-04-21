package fr.destiny.vacuum.web.service;

import com.google.common.collect.ImmutableMap;
import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import fr.destiny.vacuum.web.model.ClassType;
import fr.destiny.vacuum.web.model.GearBucketHash;
import fr.destiny.vacuum.web.model.ItemCategory;
import fr.destiny.vacuum.web.model.ItemInstance;
import fr.destiny.vacuum.web.repository.DestinyInventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static fr.destiny.vacuum.web.utils.Utils.mergeMaps;


@Service
public class ItemService {

    @Autowired
    private Destiny2Api destiny2Api;

    @Autowired
    private DestinyInventoryItemRepository itemRepository;

    Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions;

    ItemService(Destiny2Api destiny2Api, DestinyInventoryItemRepository itemRepository) {
        this.destiny2Api = destiny2Api;
        this.itemRepository = itemRepository;
        this.itemDefinitions = ImmutableMap.copyOf(
                itemRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(DestinyDefinitionsDestinyInventoryItemDefinition::getHash,
                                item -> item))
        );
    }

    public List<ItemInstance> getItemInstances(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType);

        Map<Long, Set<Long>> instanceIdsByItemHash = new HashMap<>();

        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getEquippedItems(profile));
        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getCharacterInventoryItems(profile));
        instanceIdsByItemHash = mergeMaps(instanceIdsByItemHash, getVaultItems(profile));

        Map<String, DestinyEntitiesItemsDestinyItemInstanceComponent> instances = profile.getItemComponents().getInstances().getData();
        Map<String, DestinyEntitiesItemsDestinyItemSocketsComponent> sockets = profile.getItemComponents().getSockets().getData();

        return generateItemInstances(instanceIdsByItemHash, instances, sockets, classType, itemCategory);
    }

    private List<ItemInstance> generateItemInstances(Map<Long, Set<Long>> instanceIdsByItemHash, Map<String, DestinyEntitiesItemsDestinyItemInstanceComponent> instances, Map<String, DestinyEntitiesItemsDestinyItemSocketsComponent> sockets, ClassType classType, ItemCategory itemCategory) {
        List<ItemInstance> itemInstances = new ArrayList<>();
        instanceIdsByItemHash.forEach((itemHash, instanceIds) -> {
            DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition = itemDefinitions.get(itemHash);
            ClassType itemClassType = ClassType.fromHash(itemDefinition.getClassType());

            if (classType != ClassType.ANY
                    && itemClassType != ClassType.ANY
                    && classType != itemClassType) {
                return;
            }

            if (!itemDefinition.getItemCategoryHashes().contains(itemCategory.getHash())) {
                return;
            }

            instanceIds.forEach(instanceId ->
                    itemInstances.add(new ItemInstance(
                            instanceId,
                            instances.get(Long.toString(instanceId)),
                            sockets.get(Long.toString(instanceId)),
                            itemDefinition,
                            itemDefinitions
                    )));
        });

        return itemInstances;
    }

    private Map<Long, Set<Long>> getVaultItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Long>> itemInstanceIds = new HashMap<>();

        DestinyEntitiesInventoryDestinyInventoryComponent data = profile.getProfileInventory().getData();
        if (data != null) {
            itemInstanceIds = mergeMaps(itemInstanceIds, collectItemHashes(data));
        }

        return itemInstanceIds;
    }

    private Map<Long, Set<Long>> getCharacterInventoryItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Long>> itemInstanceIds = new HashMap<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterInventories().getData();
        if (datas != null) {
            for (DestinyEntitiesInventoryDestinyInventoryComponent characterInvetory : datas.values()) {
                itemInstanceIds = mergeMaps(itemInstanceIds, collectItemHashes(characterInvetory));
            }
        }

        return itemInstanceIds;
    }

    private Map<Long, Set<Long>> getEquippedItems(DestinyResponsesDestinyProfileResponse profile) {
        Map<Long, Set<Long>> itemInstanceIds = new HashMap<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterEquipment().getData();
        if (datas != null) {
            for (DestinyEntitiesInventoryDestinyInventoryComponent characterEquipment : datas.values()) {
                itemInstanceIds = mergeMaps(itemInstanceIds, collectItemHashes(characterEquipment));

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

    private Set<DestinyDefinitionsDestinyInventoryItemDefinition> restieveItemsFromManifest(ClassType classType, ItemCategory itemCategory, Set<Long> hashes) {
        return itemRepository.findAllById(hashes)
                .stream()
                .filter(item -> {
                    ClassType itemClassType = ClassType.fromHash(item.getClassType());
                    return classType == ClassType.ANY || itemClassType == ClassType.ANY || itemClassType == classType;
                })
                .filter(item -> item.getItemCategoryHashes().contains(itemCategory.getHash()))
                .collect(Collectors.toSet());
    }

    private Map<Long, Set<Long>> collectItemHashes(DestinyEntitiesInventoryDestinyInventoryComponent data) {
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
                                        .map(DestinyEntitiesItemsDestinyItemComponent::getItemInstanceId)
                                        .collect(Collectors.toSet())
                        )
                );
    }
}
