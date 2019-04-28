package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserApi userApi;

    @Mock
    private Destiny2Api destiny2Api;

    @InjectMocks
    private UserService userService;

    @Test
    void dummyTest() {
    }
}
