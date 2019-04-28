package fr.destiny.benedict.web.service;

import com.google.common.collect.ImmutableMap;
import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import fr.destiny.benedict.web.model.*;
import fr.destiny.benedict.web.repository.DestinyInventoryItemRepository;
import fr.destiny.benedict.web.utils.PerkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static fr.destiny.benedict.web.utils.Utils.mergeMaps;


@Service
public class ItemService {

    private Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions;

    private Destiny2Api destiny2Api;

    ItemService(@Autowired Destiny2Api destiny2Api, @Autowired DestinyInventoryItemRepository itemRepository) {
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

    public Map<ItemCategory, Set<ItemInstance>> getItemInstances(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType);

        Map<Long, Set<Long>> instanceIdsByItemHash = new HashMap<>();

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

        return generateItemInstances(instanceIdsByItemHash, instances, sockets, classType, itemCategory);
    }

    private Map<ItemCategory, Set<ItemInstance>> generateItemInstances(
            Map<Long, Set<Long>> instanceIdsByItemHash,
            Map<String, DestinyEntitiesItemsDestinyItemInstanceComponent> instances,
            Map<String, DestinyEntitiesItemsDestinyItemSocketsComponent> sockets,
            ClassType classType, ItemCategory itemCategory) {
        Map<ItemCategory, Set<ItemInstance>> itemInstances = new HashMap<>();
        instanceIdsByItemHash.forEach((itemHash, instanceIds) -> {
            DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition = itemDefinitions.get(itemHash);
            ClassType itemClassType = ClassType.fromHash(itemDefinition.getClassType());

            if (classType != ClassType.ANY
                    && itemClassType != ClassType.ANY
                    && classType != itemClassType) {
                return;
            }

            final ItemCategory preciseItemCategory = itemCategory == ItemCategory.ARMOR ?
                    ItemCategory.fromSubType(itemDefinition.getItemSubType()) : itemCategory;

            if (!itemDefinition.getItemCategoryHashes().contains(itemCategory.getHash())) {
                return;
            }

            instanceIds.forEach(instanceId ->
                    {
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent = sockets.get(Long.toString(instanceId));
                        if (socketsComponent == null) {
                            socketsComponent =
                                    new DestinyEntitiesItemsDestinyItemSocketsComponent();
                            socketsComponent.setSockets(Collections.emptyList());
                        }

                        itemInstances.computeIfAbsent(
                                preciseItemCategory,
                                set -> new HashSet<>())
                                .add(
                                        new ItemInstance(
                                                instanceId,
                                                instances.get(Long.toString(instanceId)),
                                                socketsComponent,
                                                itemDefinition,
                                                itemDefinitions
                                        )
                                );
                    }
            );
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

    public Map<String, List<Perk>> getAllPerksPerSlot() {
        HashMap<String, List<Perk>> perksPerSlot = new HashMap<>();

        perksPerSlot.put(
                "helmetFirstPerks",
                PerkUtils.HELMET_FIRST_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "helmetSecondPerks",
                PerkUtils.HELMET_SECOND_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "gauntletsFirstPerks",
                PerkUtils.GAUNTLETS_FIRST_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "gauntletsSecondPerks",
                PerkUtils.GAUNTLETS_SECOND_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "chestArmorFirstPerks",
                PerkUtils.CHEST_ARMOR_FIRST_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "chestArmorSecondPerks",
                PerkUtils.CHEST_ARMOR_SECOND_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "legArmorFirstPerks",
                PerkUtils.LEG_ARMOR_FIRST_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "legArmorSecondPerks",
                PerkUtils.LEG_ARMOR_SECOND_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "classArmorFirstPerks",
                PerkUtils.CLASS_ARMOR_FIRST_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        perksPerSlot.put(
                "classArmorSecondPerks",
                PerkUtils.CLASS_ARMOR_SECOND_PERKS
                        .stream()
                        .map(perkHash -> new Perk(itemDefinitions.get(perkHash)))
                        .collect(Collectors.toList())
        );
        return perksPerSlot;
    }
}
