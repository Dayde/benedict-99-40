package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.UserApi;
import fr.destiny.api.model.InlineResponse2003;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacuumServiceTest {

    @Mock
    private UserApi userApi;

    @InjectMocks
    private VacuumService vacuumService;

    @Test
    public void userInfo() {
        // Given
        String username = "Daydraeck";
        InlineResponse2003 mockResponse = mock(InlineResponse2003.class);
        when(userApi.userSearchUsers(username)).thenReturn(mockResponse);

        // When
        InlineResponse2003 result = vacuumService.userInfo(username);

        // Then
        assertThat(result).isEqualTo(mockResponse);
        verify(userApi).userSearchUsers(username);
    }
}
