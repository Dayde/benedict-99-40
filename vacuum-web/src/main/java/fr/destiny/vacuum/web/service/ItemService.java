package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import fr.destiny.vacuum.web.repository.DestinyInventoryItemRepository;
import fr.destiny.vacuum.web.utils.ClassType;
import fr.destiny.vacuum.web.utils.GearBucketHashEnum;
import fr.destiny.vacuum.web.utils.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ItemService {

    @Autowired
    private Destiny2Api destiny2Api;

    @Autowired
    private DestinyInventoryItemRepository itemRepository;

    public List<DestinyDefinitionsDestinyInventoryItemDefinition> getAllItems(Long membershipId, Integer membershipType, ClassType classType, ItemCategory itemCategory) {
        DestinyResponsesDestinyProfileResponse profile = destiny2Api
                .destiny2GetProfile(
                        membershipId,
                        membershipType,
                        Arrays.asList(
                                DestinyDestinyComponentType.NUMBER_102.getValue(),
                                DestinyDestinyComponentType.NUMBER_201.getValue()
                        )
                ).getResponse();

        List<Long> hashes = new ArrayList<>();
        DestinyEntitiesInventoryDestinyInventoryComponent data = profile.getProfileInventory()
                .getData();
        if (data != null) {
            hashes.addAll(data
                    .getItems()
                    .stream()
                    .filter(item -> GearBucketHashEnum.fromHash(item.getBucketHash()) != null)
                    .map(DestinyEntitiesItemsDestinyItemComponent::getItemHash)
                    .collect(Collectors.toList()));
        }

        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> datas = profile.getCharacterInventories().getData();
        if (datas != null) {
            for (DestinyEntitiesInventoryDestinyInventoryComponent characterInvetory : datas.values()) {
                hashes.addAll(characterInvetory.getItems()
                        .stream()
                        .filter(item -> GearBucketHashEnum.fromHash(item.getBucketHash()) != null)
                        .map(DestinyEntitiesItemsDestinyItemComponent::getItemHash)
                        .collect(Collectors.toList()));
            }
        }

        return itemRepository.findAllById(hashes)
                .stream()
                .filter(item -> {
                    ClassType itemClassType = ClassType.fromHash(item.getClassType());
                    return classType == ClassType.ANY || itemClassType == ClassType.ANY || itemClassType == classType;
                })
                .filter(item -> item.getItemCategoryHashes().contains(itemCategory.getHash()))
                .collect(Collectors.toList());
    }
}
