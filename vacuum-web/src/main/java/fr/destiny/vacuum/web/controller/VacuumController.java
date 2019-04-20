package fr.destiny.vacuum.web.controller;

import fr.destiny.api.model.BungieMembershipType;
import fr.destiny.vacuum.web.service.ItemService;
import fr.destiny.vacuum.web.service.UserService;
import fr.destiny.vacuum.web.model.ClassType;
import fr.destiny.vacuum.web.model.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
    public ModelAndView items(@RequestParam String username, @RequestParam String platform, @RequestParam String classType, @RequestParam String itemCategory) {
        Integer membershipType = BungieMembershipType.fromValue(platform).getValue();
        Long membershipId = userService.destinyMembershipid(username, membershipType);

        Map<String, Object> model = new HashMap<>();
        model.put(
                "items",
                itemService.getItemInstances(
                        membershipId,
                        membershipType,
                        ClassType.valueOf(classType),
                        ItemCategory.valueOf(itemCategory)
                )
        );
        return new ModelAndView("index", model);
    }
}
