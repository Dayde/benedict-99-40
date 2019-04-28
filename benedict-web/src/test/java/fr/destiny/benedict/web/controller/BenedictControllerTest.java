package fr.destiny.benedict.web.controller;

import fr.destiny.benedict.web.service.ItemService;
import fr.destiny.benedict.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BenedictControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BenedictController benedictController;

    @Test
    public void dummyTest() {
    }
}
