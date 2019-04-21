package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.name, ((Perk) obj).name);
    }
}
