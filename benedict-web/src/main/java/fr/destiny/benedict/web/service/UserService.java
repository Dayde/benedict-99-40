package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import fr.destiny.api.model.UserGeneralUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
