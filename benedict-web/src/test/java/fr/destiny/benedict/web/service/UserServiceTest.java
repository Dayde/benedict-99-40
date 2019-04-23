package fr.destiny.benedict.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import fr.destiny.api.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserApi userApi;

    @Mock
    private Destiny2Api destiny2Api;

    @InjectMocks
    private UserService userService;

    @Test
    void userInfo_shouldRetrieveUserInfoThroughUserApi() {
        // Given
        String username = "Daydraeck";
        InlineResponse2003 mockResponse = mock(InlineResponse2003.class);
        when(userApi.userSearchUsers(username)).thenReturn(mockResponse);
        UserGeneralUser expectedUser = mock(UserGeneralUser.class);
        when(mockResponse.getResponse()).thenReturn(Arrays.asList(expectedUser));

        // When
        UserGeneralUser result = userService.userInfo(username);

        // Then
        assertThat(result).isEqualTo(expectedUser);
        verify(userApi).userSearchUsers(username);
    }

    @Test
    void destinyMembershipId_shouldRetrieveDestinyMembershipIdOfGivenUsername() {
        // Given
        String username = "Daydraeck";
        Integer membershipType = BungieMembershipType.NUMBER_2.getValue();
        InlineResponse20032 mock = mock(InlineResponse20032.class);
        when(destiny2Api.destiny2SearchDestinyPlayer(username, membershipType)).thenReturn(mock);
        UserUserInfoCard userInfoCard = mock(UserUserInfoCard.class);
        when(mock.getResponse()).thenReturn(Arrays.asList(userInfoCard));
        long expectedMembershipid = 1l;
        when(userInfoCard.getMembershipId()).thenReturn(expectedMembershipid);

        // When
        Long result = userService.destinyMembershipid(username, membershipType);

        // Then
        assertThat(result).isEqualTo(expectedMembershipid);
    }
}
