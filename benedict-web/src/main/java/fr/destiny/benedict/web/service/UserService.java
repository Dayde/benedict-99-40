package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import fr.destiny.benedict.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


@Service
public class UserService {

    private final Destiny2Api destiny2Api;
    private final ItemService itemService;
    private final UserApi userApi;

    public UserService(
            @Autowired Destiny2Api destiny2Api,
            @Autowired ItemService itemService,
            @Autowired @Qualifier("userApiScoped") UserApi userApi) {
        this.destiny2Api = destiny2Api;
        this.itemService = itemService;
        this.userApi = userApi;
    }

    public List<User> findUsersByUsernameAndPlatform(String username, int membershipType) {
        String urlEncodedUsername;
        try {
            urlEncodedUsername = URLEncoder.encode(username, UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return Collections.emptyList();
        }
        return destiny2Api.destiny2SearchDestinyPlayer(urlEncodedUsername, membershipType, false)
                .getResponse()
                .stream()
                .map(User::new)
                .collect(Collectors.toList());
    }

    public User findUserByUserIdAndPlatform(long userId, int platform) {
        return new User(destiny2Api.destiny2GetProfile(userId, platform, Arrays.asList(100, 200)).getResponse(), itemService.getItemDefinitions());
    }

    public User currentUser(String token) {
        userApi.getApiClient().addDefaultHeader("Authorization", "Bearer " + token);
        return new User(userApi.userGetMembershipDataForCurrentUser().getResponse().getDestinyMemberships().get(0));
    }
}
