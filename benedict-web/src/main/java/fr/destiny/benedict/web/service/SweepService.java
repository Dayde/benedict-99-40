package fr.destiny.benedict.web.service;

import fr.destiny.benedict.web.model.ClassType;
import fr.destiny.benedict.web.model.ItemCategory;
import fr.destiny.benedict.web.model.ItemInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SweepService {

    private ItemService itemService;


    SweepService(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    public List<ItemInstance> sweep(
            long userId,
            int platform,
            ClassType classType,
            ItemCategory itemCategory,
            String token) {

        Set<ItemInstance> itemInstances = itemService.getItemInstances(
                userId,
                platform,
                classType,
                itemCategory,
                token
        );

        // We want to sort legendary stuff only
        return itemInstances.stream()
                .filter(item -> "Legendary".equals(item.getTierType()))
                .collect(Collectors.toList());
    }
}
