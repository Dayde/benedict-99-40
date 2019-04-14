package fr.destiny.vacuum.web.controller;

import fr.destiny.api.model.UserGeneralUser;
import fr.destiny.vacuum.web.service.ItemService;
import fr.destiny.vacuum.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacuumControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private VacuumController vacuumController;

    @Test
    public void userInfo_shouldCallServiceAndReturnTheResult() {
        // Given
        String username = "Daydraeck";
        UserGeneralUser mockResponse = mock(UserGeneralUser.class);
        when(userService.userInfo(username)).thenReturn(mockResponse);
        String fakeResult = "toto";
        when(mockResponse.toString()).thenReturn(fakeResult);

        // When
        String result = vacuumController.userInfo(username);

        // Then
        assertThat(result).isEqualTo(fakeResult);
        verify(userService).userInfo(username);
    }
}
