package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.Objects;

public class Perk {
    private long hash;
    private String name;
    private String icon;
    private boolean primary;

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition) {
        this(itemDefinition, true);
    }

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition, boolean primary) {
        this.hash = itemDefinition.getHash();
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.primary = primary;
    }

    public long getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isPrimary() {
        return primary;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hash);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.hash, ((Perk) obj).hash);
    }
}
