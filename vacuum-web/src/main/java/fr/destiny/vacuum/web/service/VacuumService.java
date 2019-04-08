package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.UserApi;
import fr.destiny.api.model.InlineResponse2003;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VacuumService {

    @Autowired
    private UserApi user;

    public InlineResponse2003 userInfo(String username) {
        return user.userSearchUsers(username);
    }
}
