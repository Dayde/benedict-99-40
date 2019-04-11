package fr.destiny.vacuum.web.controller;

import fr.destiny.api.model.InlineResponse2003;
import fr.destiny.vacuum.web.service.VacuumService;
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
    private VacuumService vacuumService;

    @InjectMocks
    private VacuumController vacuumController;

    @Test
    public void userInfo_shouldCallServiceAndReturnTheResult() {
        // Given
        String username = "Daydraeck";
        InlineResponse2003 mockResponse = mock(InlineResponse2003.class);
        when(vacuumService.userInfo(username)).thenReturn(mockResponse);
        String fakeResult = "toto";
        when(mockResponse.toString()).thenReturn(fakeResult);

        // When
        String result = vacuumController.userInfo(username);

        // Then
        assertThat(result).isEqualTo(fakeResult);
        verify(vacuumService).userInfo(username);
    }
}
