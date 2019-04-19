package fr.destiny.vacuum.web.controller;

import fr.destiny.api.model.BungieMembershipType;
import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.vacuum.web.service.ItemService;
import fr.destiny.vacuum.web.service.UserService;
import fr.destiny.vacuum.web.utils.ClassType;
import fr.destiny.vacuum.web.utils.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static fr.destiny.vacuum.web.config.VacuumConfig.BUNGIE_ROOT_URL;

@RestController
public class VacuumController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/")
    public String userInfo(@RequestParam String username) {
        return userService.userInfo(username).toString();
    }

    @RequestMapping("/items")
    public String items(@RequestParam String username, @RequestParam String platform, @RequestParam String classType, @RequestParam String itemCategory) {
        Integer membershipType = BungieMembershipType.fromValue(platform).getValue();
        Long membershipId = userService.destinyMembershipid(username, membershipType);
        return itemService.getAllItems(
                membershipId,
                membershipType,
                ClassType.valueOf(classType),
                ItemCategory.valueOf(itemCategory)
        ).stream()
                .map(this::imgTag)
                .collect(Collectors.joining());
    }

    private String imgTag(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition) {
        return "<img src=\"" + BUNGIE_ROOT_URL + itemDefinition.getDisplayProperties().getIcon() + "\"/>";
    }
}
