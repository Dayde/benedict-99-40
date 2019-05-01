package fr.destiny.benedict.web.controller;

import com.google.common.collect.Lists;
import fr.destiny.benedict.web.model.*;
import fr.destiny.benedict.web.service.ItemService;
import fr.destiny.benedict.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BenedictController {

    private final UserService userService;

    private final ItemService itemService;

    public BenedictController(@Autowired UserService userService, @Autowired ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @RequestMapping("/users")
    public List<User> users(
            @RequestParam String username,
            @RequestParam(required = false, defaultValue = "-1") int platform) {
        return userService.findUsersByUsernameAndPlatform(username, platform);
    }

    @RequestMapping("/users/{userId}/{platform}")
    public User user(
            @PathVariable long userId,
            @PathVariable int platform) {
        return userService.findUserByUserIdAndPlatform(userId, platform);
    }

    @RequestMapping("/user")
    public User currentUser(@RequestParam String token) {
        return userService.currentUser(token);
    }

    @RequestMapping("/users/{userId}/{platform}/items")
    public Map<String, Object> items(
            @PathVariable long userId,
            @PathVariable int platform,
            @RequestParam String classType,
            @RequestParam String itemCategory,
            @RequestParam Set<Long> uncommittedPerkHashes,
            @RequestParam(required = false, defaultValue = "") String token) {

        Map<ItemCategory, Set<ItemInstance>> itemInstancesPerCategory = itemService.getItemInstances(
                userId,
                platform,
                ClassType.valueOf(classType),
                ItemCategory.valueOf(itemCategory),
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

        // Generate permutation
        Map<Set<Perk>, Set<ItemInstance>> itemsByPerkPermutation = new HashMap<>();
        itemInstances.forEach(instance -> {
            List<List<Perk>> product = Lists.cartesianProduct(instance.getPerks().stream()
                    .map(PerkChoice::getChoices)
                    .collect(Collectors.toList()));
            product.forEach(permutation -> {
                Set<Perk> finalPermutation = new HashSet<>();
                for (Perk perk : permutation) {
                    if (!uncommittedPerkHashes.contains(perk.getHash())) {
                        finalPermutation.add(perk);
                    }
                }
                Set<ItemInstance> instances = itemsByPerkPermutation.computeIfAbsent(finalPermutation, set -> new HashSet<>());
                instances.add(instance);
            });
        });

        // Keep all unique permutations
        itemsByPerkPermutation.values()
                .forEach(instances -> {
                    if (instances.size() == 1) {
                        toKeep.add(instances.iterator().next());
                    }
                });
        return toKeep;
    }

    @RequestMapping("/perks")
    public Map<ItemCategory, Map<String, List<Perk>>> perks() {
        return itemService.getAllPerksPerSlotPerArmor();
    }
}
