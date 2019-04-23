package fr.destiny.benedict.web.controller;

import fr.destiny.api.model.UserGeneralUser;
import fr.destiny.benedict.web.service.ItemService;
import fr.destiny.benedict.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BenedictControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BenedictController benedictController;

    @Test
    public void userInfo_shouldCallServiceAndReturnTheResult() {
        // Given
        String username = "Daydraeck";
        UserGeneralUser mockResponse = mock(UserGeneralUser.class);
        when(userService.userInfo(username)).thenReturn(mockResponse);
        String fakeResult = "toto";
        when(mockResponse.toString()).thenReturn(fakeResult);

        // When
        String result = benedictController.userInfo(username);

        // Then
        assertThat(result).isEqualTo(fakeResult);
        verify(userService).userInfo(username);
    }
}
