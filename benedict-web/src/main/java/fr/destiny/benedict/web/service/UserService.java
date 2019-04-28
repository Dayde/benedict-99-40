package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.benedict.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserService(@Autowired Destiny2Api destiny2Api) {
        this.destiny2Api = destiny2Api;
    }

    public List<User> findUsersByUsernameAndPlatform(String username, int membershipType) {
        String urlEncodedUsername;
        try {
            urlEncodedUsername = URLEncoder.encode(username, UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return Collections.emptyList();
        }
        return destiny2Api.destiny2SearchDestinyPlayer(urlEncodedUsername, membershipType)
                .getResponse()
                .stream()
                .map(User::new)
                .collect(Collectors.toList());
    }

    public User findUserByUserIdAndPlatform(long userId, int platform) {
        return new User(destiny2Api.destiny2GetProfile(userId, platform, Arrays.asList(100, 200)).getResponse());
    }
}
