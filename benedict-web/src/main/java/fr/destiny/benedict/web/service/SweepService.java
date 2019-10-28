package fr.destiny.benedict.web.service;

import fr.destiny.benedict.web.model.ClassType;
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

    public Map<String, Object> sweep(
            long userId,
            int platform,
            ClassType classType,
            ItemCategory itemCategory,
            Set<Long> uncommittedPerkHashes,
            String token) {

        Map<ItemCategory, Set<ItemInstance>> itemInstancesPerCategory = itemService.getItemInstances(
                userId,
                platform,
                classType,
                itemCategory,
                token
        );


        // We want to sort legendary stuff only
        for (Map.Entry<ItemCategory, Set<ItemInstance>> itemCategorySetEntry : itemInstancesPerCategory.entrySet()) {
            ItemCategory key = itemCategorySetEntry.getKey();
            Set<ItemInstance> value = itemCategorySetEntry.getValue();
            value = value.stream()
                    .filter(item -> "Legendary".equals(item.getTierType()))
                    .collect(Collectors.toSet());
            itemInstancesPerCategory.put(key, value);
        }

        Set<ItemInstance> itemInstances = itemInstancesPerCategory.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Set<ItemInstance> toKeep = itemInstancesPerCategory.values()
                .stream()
                .map(instances -> computeWhatToKeep(instances, uncommittedPerkHashes))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // if not to be kept, it needs to be sorted
        List<ItemInstance> toSortSorted = new ArrayList<>(itemInstances);
        toSortSorted.removeAll(toKeep);
        toSortSorted.sort(ItemInstance::compareTo);
        toSortSorted.sort(Collections.reverseOrder());

        List<ItemInstance> toKeepSorted = new ArrayList<>(toKeep);
        toKeepSorted.sort(ItemInstance::compareTo);
        toKeepSorted.sort(Collections.reverseOrder());


        Map<String, Object> result = new HashMap<>();
        result.put("keep", toKeepSorted);
        result.put("sort", toSortSorted);
        return result;
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
