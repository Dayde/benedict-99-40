package fr.destiny.vacuum.web.service;

import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private Destiny2Api destiny2Api;

    @InjectMocks
    private ItemService itemService;

    @Test
    void listItems_shouldRetrieveItemsOfGivenPlayerUsername() {
        // Given
        Long membershipId = 0l;
        Integer membershipType = BungieMembershipType.NUMBER_2.getValue();

        InlineResponse20034 fakeResult = mock(InlineResponse20034.class);
        when(destiny2Api.destiny2GetProfile(membershipId, membershipType, singletonList(DestinyDestinyComponentType.NUMBER_201.getValue())))
                .thenReturn(fakeResult);
        DestinyResponsesDestinyProfileResponse fakeResponse = mock(DestinyResponsesDestinyProfileResponse.class);
        when(fakeResult.getResponse()).thenReturn(fakeResponse);
        DictionaryComponentResponseOfint64AndDestinyInventoryComponent mock = mock(DictionaryComponentResponseOfint64AndDestinyInventoryComponent.class);
        when(fakeResponse.getCharacterInventories()).thenReturn(mock);
        HashMap<String, DestinyEntitiesInventoryDestinyInventoryComponent> map = new HashMap<>();
        when(mock.getData()).thenReturn(map);


        // When
        Map<String, DestinyEntitiesInventoryDestinyInventoryComponent> items = itemService.getAllItems(membershipId, membershipType);

        // Then
        assertThat(items).isEqualTo(map);
    }
}
