package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyDestinyComponentType;
import fr.destiny.vacuum.web.repository.DestinyInventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;


@Service
public class ItemService {

    @Autowired
    private Destiny2Api destiny2Api;

    @Autowired
    private DestinyInventoryItemRepository itemRepository;

    public DestinyDefinitionsDestinyInventoryItemDefinition getFirstItem(Long membershipId, Integer membershipType) {
        return itemRepository.findById(destiny2Api
                .destiny2GetProfile(membershipId, membershipType, singletonList(DestinyDestinyComponentType.NUMBER_201.getValue()))
                .getResponse()
                .getCharacterInventories()
                .getData()
                .entrySet()
                .iterator()
                .next()
                .getValue()
                .getItems()
                .iterator()
                .next()
                .getItemHash());

    }

}
