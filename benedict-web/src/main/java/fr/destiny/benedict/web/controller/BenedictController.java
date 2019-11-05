package fr.destiny.benedict.web.controller;

import fr.destiny.benedict.web.model.*;
import fr.destiny.benedict.web.service.ItemService;
import fr.destiny.benedict.web.service.SweepService;
import fr.destiny.benedict.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@RestController
@RequestMapping("/api")
public class BenedictController {

    private final UserService userService;

    private final ItemService itemService;

    private final SweepService sweepService;

    public BenedictController(
            @Autowired UserService userService,
            @Autowired ItemService itemService,
            @Autowired SweepService sweepService) {
        this.userService = userService;
        this.itemService = itemService;
        this.sweepService = sweepService;
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
    public SortedMap<ExtraModEnum, List<ItemInstance>> items(
            @PathVariable long userId,
            @PathVariable int platform,
            @RequestParam String classType,
            @RequestParam String itemCategory,
            @RequestParam(required = false, defaultValue = "") String token) {
        return sweepService.sweep(
                userId,
                platform,
                ClassType.valueOf(classType),
                ItemCategory.valueOf(itemCategory),
                token
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> httpExceptionHandler(HttpClientErrorException httpError) {
        return new ResponseEntity<>(httpError.getMessage(), HttpStatus.valueOf(httpError.getRawStatusCode()));
    }

    @RequestMapping(value = "/users/{userId}/{platform}/items/{itemHash}/{instanceId}", method = RequestMethod.PUT)
    public String transferItem(
            @PathVariable long userId,
            @PathVariable int platform,
            @PathVariable long itemHash,
            @PathVariable long instanceId,
            @RequestParam String token,
            @RequestParam String currentLocation,
            @RequestParam String targetLocation,
            HttpServletResponse response) {
        String status = itemService.transferItem(token, platform, itemHash, instanceId, currentLocation, targetLocation);
        if (!"Ok".equals(status)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return status;
    }

    @RequestMapping("/perks")
    public Map<ItemCategory, Map<String, List<Perk>>> perks() {
        return itemService.getAllPerksPerSlotPerArmor();
    }
}
