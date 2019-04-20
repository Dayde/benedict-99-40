package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

public class Perk {
    private String name;
    private String icon;

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition) {
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
