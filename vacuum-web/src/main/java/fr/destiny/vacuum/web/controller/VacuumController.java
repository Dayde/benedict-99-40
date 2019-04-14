package fr.destiny.vacuum.web.controller;

import fr.destiny.api.model.BungieMembershipType;
import fr.destiny.vacuum.web.service.ItemService;
import fr.destiny.vacuum.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String items(@RequestParam String username, @RequestParam String platform) {
        Integer membershipType = BungieMembershipType.fromValue(platform).getValue();
        Long membershipId = userService.destinyMembershipid(username, membershipType);
        return itemService.getAllItems(membershipId, membershipType).toString();
    }
}
