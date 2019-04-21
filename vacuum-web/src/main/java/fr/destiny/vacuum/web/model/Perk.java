package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.Objects;

public class Perk {
    private long hash;
    private String name;
    private String icon;

    Perk(DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition) {
        this.hash = itemDefinition.getHash();
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
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

    @Override
    public int hashCode() {
        return Objects.hashCode(hash);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.hash, ((Perk) obj).hash);
    }
}
