package fr.destiny.benedict.web.service;

import fr.destiny.benedict.web.model.ClassType;
import fr.destiny.benedict.web.model.ExtraModEnum;
import fr.destiny.benedict.web.model.ItemCategory;
import fr.destiny.benedict.web.model.ItemInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SweepService {

    private ItemService itemService;


    SweepService(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    public SortedMap<ExtraModEnum, List<ItemInstance>> sweep(
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
        itemInstances = itemInstances.stream()
                .filter(item -> "Legendary".equals(item.getTierType()))
                .collect(Collectors.toSet());

        return itemInstances.stream()
                .sorted()
                .collect(Collectors.groupingBy(ItemInstance::getExtraMod, TreeMap::new, Collectors.toList()));
    }

    private Set<ItemInstance> computeWhatToKeep(Set<ItemInstance> itemInstances, Set<Long> uncommittedPerkHashes) {

        Map<String, List<ItemInstance>> instancesGroupByNames = itemInstances.stream()
                .collect(Collectors.groupingBy(ItemInstance::getName));

        Set<ItemInstance> toKeep = new HashSet<>();

        // Keep all armor that come in a unique exemplary
        instancesGroupByNames.values().forEach(instances -> {
            if (instances.size() == 1) {
                toKeep.add(instances.get(0));
            }
        });

        return toKeep;
    }
}
