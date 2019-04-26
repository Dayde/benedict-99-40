package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.Objects;

public class Perk {
    private long hash;
    private String name;
    private String icon;
    private boolean primary;
    private boolean current;

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition, boolean current) {
        this(itemDefinition, true, current);
    }

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition, boolean primary, boolean current) {
        this.hash = itemDefinition.getHash();
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.primary = primary;
        this.current = current;
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

    public boolean isCurrent() {
        return current;
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
