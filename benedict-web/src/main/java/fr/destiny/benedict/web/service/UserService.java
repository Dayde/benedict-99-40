package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import fr.destiny.api.model.UserGeneralUser;
import fr.destiny.benedict.web.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


@Service
public class UserService {

    @Autowired
    private UserApi userApi;

    @Autowired
    private Destiny2Api destiny2Api;

    public UserGeneralUser userInfo(String username) {
        return userApi.userSearchUsers(username).getResponse().get(0);
    }

    public Long destinyMembershipid(String username, Integer membershipType) {
        return destiny2Api.destiny2SearchDestinyPlayer(username, membershipType).getResponse().get(0).getMembershipId();
    }

    public List<Player> retrievePlayer(String username) {
        try {
            return destiny2Api.destiny2SearchDestinyPlayer(URLEncoder.encode(username, UTF_8.toString()), -1).getResponse().stream().map(player -> new Player(player.getDisplayName(), player.getMembershipType())).collect(Collectors.toList());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
