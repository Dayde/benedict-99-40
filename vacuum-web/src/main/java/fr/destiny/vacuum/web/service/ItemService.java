package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import fr.destiny.vacuum.web.repository.DestinyInventoryItemRepository;
import fr.destiny.vacuum.web.utils.ClassType;
import fr.destiny.vacuum.web.utils.GearBucketHashEnum;
import fr.destiny.vacuum.web.utils.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ItemService {

    @Autowired
    private Destiny2Api destiny2Api;

    @Autowired
    private DestinyInventoryItemRepository itemRepository;

    public Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> getAllItemsDefinitions(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        Set<DestinyDefinitionsDestinyInventoryItemDefinition> allItems = new HashSet<>();
        allItems.addAll(getEquippedItems(membershipId, membershipType, classType, itemCategory));
        allItems.addAll(getCharacterInventoryItems(membershipId, membershipType, classType, itemCategory));
        allItems.addAll(getVaultItems(membershipId, membershipType, classType, itemCategory));
        return allItems.stream().collect(Collectors.toMap(DestinyDefinitionsDestinyInventoryItemDefinition::getHash, item -> item));
    }

    private Set<DestinyDefinitionsDestinyInventoryItemDefinition> getVaultItems(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType, DestinyDestinyComponentType.NUMBER_102);

        Set<Long> hashes = new HashSet<>();

        DestinyEntitiesInventoryDestinyInventoryComponent data = profile.getProfileInventory().getData();
        if (data != null) {
            hashes.addAll(collectItemHashes(data));
        }

        return restieveItemsFromManifest(classType, itemCategory, hashes);
    }

    private Set<DestinyDefinitionsDestinyInventoryItemDefinition> getCharacterInventoryItems(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType, DestinyDestinyComponentType.NUMBER_201);

        Set<Long> hashes = new HashSet<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterInventories().getData();
        if (datas != null) {
            for (DestinyEntitiesInventoryDestinyInventoryComponent characterInvetory : datas.values()) {
                hashes.addAll(collectItemHashes(characterInvetory));
            }
        }

        return restieveItemsFromManifest(classType, itemCategory, hashes);
    }

    private Set<DestinyDefinitionsDestinyInventoryItemDefinition> getEquippedItems(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = getProfile(membershipId, membershipType, DestinyDestinyComponentType.NUMBER_205);

        Set<Long> hashes = new HashSet<>();

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterEquipment().getData();
        if (datas != null) {
            for (DestinyEntitiesInventoryDestinyInventoryComponent characterEquipment : datas.values()) {
                hashes.addAll(collectItemHashes(characterEquipment));
            }
        }

        return restieveItemsFromManifest(classType, itemCategory, hashes);
    }

    private DestinyResponsesDestinyProfileResponse getProfile(Long membershipId, Integer membershipType, DestinyDestinyComponentType... components) {
        return destiny2Api
                .destiny2GetProfile(
                        membershipId,
                        membershipType,
                        Arrays.stream(components).map(DestinyDestinyComponentType::getValue).collect(Collectors.toList())
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

    private Set<Long> collectItemHashes(DestinyEntitiesInventoryDestinyInventoryComponent data) {
        return data
                .getItems()
                .stream()
                .filter(item -> GearBucketHashEnum.fromHash(item.getBucketHash()) != null)
                .map(DestinyEntitiesItemsDestinyItemComponent::getItemHash)
                .collect(Collectors.toSet());
    }
}
