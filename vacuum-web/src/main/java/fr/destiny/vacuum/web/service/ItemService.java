package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.DestinyDestinyComponentType;
import fr.destiny.api.model.DestinyEntitiesInventoryDestinyInventoryComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Collections.singletonList;


@Service
public class ItemService {

    @Autowired
    private Destiny2Api destiny2Api;

    public Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> getAllItems(Long membershipId, Integer membershipType) {
        return destiny2Api
                .destiny2GetProfile(membershipId, membershipType, singletonList(DestinyDestinyComponentType.NUMBER_201.getValue()))
                .getResponse().getCharacterInventories().getData();

    }

}
