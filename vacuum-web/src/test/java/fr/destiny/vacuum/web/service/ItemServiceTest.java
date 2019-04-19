package fr.destiny.vacuum.web.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.model.BungieMembershipType;
import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.InlineResponse20034;
import fr.destiny.vacuum.web.repository.DestinyInventoryItemRepository;
import fr.destiny.vacuum.web.utils.ClassType;
import fr.destiny.vacuum.web.utils.ItemCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private DestinyInventoryItemRepository itemRepository;

    @Mock
    private Destiny2Api destiny2Api;

    @InjectMocks
    private ItemService itemService;

    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    void listItems_shouldRetrieveItemsOfGivenPlayerUsername() {
        // Given
        Long membershipId = 4611686018486385797L;
        Integer membershipType = BungieMembershipType.NUMBER_2.getValue();
        InlineResponse20034 profile = loadProfileJsonFile();
        DestinyDefinitionsDestinyInventoryItemDefinition item = loadItemJsonFile();

        when(destiny2Api.destiny2GetProfile(anyLong(), anyInt(), anyList()))
                .thenReturn(profile);
        when(
                itemRepository.findAllById(
                        new HashSet<>(
                                Arrays.asList(
                                        2208405142L,
                                        324382200L
                                )
                        )
                )
        ).thenReturn(singleton(item));

        // When
        DestinyDefinitionsDestinyInventoryItemDefinition firstItem =
                itemService.getAllItemsDefinitions(membershipId, membershipType, ClassType.ANY, ItemCategory.WEAPON)
                        .get(2286143274L);

        assertThat(firstItem.getDisplayProperties().getName()).isEqualTo("The Huckleberry");
    }

    private DestinyDefinitionsDestinyInventoryItemDefinition loadItemJsonFile() {
        return loadJsonFile("huckleberry.json", DestinyDefinitionsDestinyInventoryItemDefinition.class);
    }

    private InlineResponse20034 loadProfileJsonFile() {
        return loadJsonFile("profile.json", InlineResponse20034.class);
    }

    private <T> T loadJsonFile(String resourceName, Class<T> clazz) {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + resourceName)) {
            return mapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
