package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyResponsesDestinyProfileResponse;
import fr.destiny.api.model.UserUserInfoCard;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class User {
    private String username;
    private long userId;
    private int platform;
    private Map<Long, DestinyCharacter> characters;

    public User(UserUserInfoCard player) {
        this.username = player.getDisplayName();
        this.userId = player.getMembershipId();
        this.platform = player.getMembershipType();
        this.characters = new HashMap<>();
    }

    public User(DestinyResponsesDestinyProfileResponse profile, Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this(profile.getProfile().getData().getUserInfo());
        this.characters.putAll(
                profile.getCharacters()
                        .getData()
                        .values()
                        .stream()
                        .map(character -> new DestinyCharacter(character, itemDefinitions))
                        .collect(Collectors.toMap(
                                DestinyCharacter::getCharacterId,
                                character -> character
                        ))
        );
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public int getPlatform() {
        return platform;
    }

    public Map<Long, DestinyCharacter> getCharacters() {
        return characters;
    }
}
