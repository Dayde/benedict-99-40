package fr.destiny.benedict.web.controller;

import com.google.common.collect.Lists;
import fr.destiny.api.model.BungieMembershipType;
import fr.destiny.benedict.web.model.*;
import fr.destiny.benedict.web.service.ItemService;
import fr.destiny.benedict.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BenedictController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/user")
    public String userInfo(@RequestParam String username) {
        return userService.userInfo(username).toString();
    }

    @RequestMapping("/player")
    public List<Player> playerInfo(@RequestParam String username) {
        return userService.retrievePlayer(username);
    }

    @RequestMapping("/items")
    public Map<String, Object> items(@RequestParam String username, @RequestParam String platform, @RequestParam String classType, @RequestParam String itemCategory) {
        Integer membershipType = BungieMembershipType.fromValue(platform).getValue();
        Long membershipId = userService.destinyMembershipid(username, membershipType);

        Set<ItemInstance> itemInstances = new HashSet<>(itemService.getItemInstances(
                membershipId,
                membershipType,
                ClassType.valueOf(classType),
                ItemCategory.valueOf(itemCategory)
        ));

        // We want to sort legendary stuff only
        itemInstances = itemInstances.stream()
                .filter(item -> "Legendary".equals(item.getTierType()))
                .collect(Collectors.toSet());

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
        Map<List<Perk>, List<ItemInstance>> itemsByPerkPermutation = new HashMap<>();
        itemInstances.forEach(instance -> {
            List<List<Perk>> product = Lists.cartesianProduct(instance.getPerks().stream()
                    .map(PerkChoice::getChoices)
                    .collect(Collectors.toList()));
            product.forEach(pair -> {
                List<ItemInstance> instances = itemsByPerkPermutation.computeIfAbsent(pair, list -> new ArrayList<>());
                instances.add(instance);
            });
        });

        // Keep all unique permutations
        itemsByPerkPermutation.values()
                .forEach(instances -> {
                    if (instances.size() == 1) {
                        toKeep.add(instances.get(0));
                    }
                });

        // if not to be kept, it needs to be sorted
        List<ItemInstance> toSortSorted = new ArrayList<>(itemInstances);
        toSortSorted.removeAll(toKeep);
        toSortSorted.sort(ItemInstance::compareTo);
        toSortSorted.sort(Comparator.reverseOrder());

        List<ItemInstance> toKeepSorted = new ArrayList<>(toKeep);
        toKeepSorted.sort(ItemInstance::compareTo);
        toKeepSorted.sort(Comparator.reverseOrder());


        Map<String, Object> result = new HashMap<>();
        result.put("keep", toKeepSorted);
        result.put("sort", toSortSorted);
        return result;
    }

    @RequestMapping("/perks")
    public Map<String, List<Perk>> perks() {
        return itemService.getAllPerksPerSlot();
    }
}
