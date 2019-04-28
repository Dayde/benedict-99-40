package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyEntitiesCharactersDestinyCharacterComponent;

import java.util.Map;

class DestinyCharacter {

    private long characterId;
    private ClassType classType;
    private String emblemIcon;
    private String emblemBackground;

    DestinyCharacter(
            DestinyEntitiesCharactersDestinyCharacterComponent character,
            Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this.characterId = character.getCharacterId();
        this.classType = ClassType.fromHash(character.getClassType());
        DestinyDefinitionsDestinyInventoryItemDefinition emblem = itemDefinitions.get(character.getEmblemHash());
        this.emblemIcon = emblem.getSecondaryOverlay();
        this.emblemBackground = emblem.getSecondarySpecial();
    }

    public long getCharacterId() {
        return characterId;
    }

    public ClassType getClassType() {
        return classType;
    }

    public String getEmblemIcon() {
        return emblemIcon;
    }

    public String getEmblemBackground() {
        return emblemBackground;
    }
}
