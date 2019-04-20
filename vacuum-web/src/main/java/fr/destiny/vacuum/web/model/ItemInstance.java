package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemInstanceComponent;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemSocketState;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemSocketsComponent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemInstance {

    private String name;
    private String icon;
    private int powerLevel;
    private List<PerkChoice> perks;

    public ItemInstance(DestinyEntitiesItemsDestinyItemInstanceComponent instance,
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent,
                        DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition,
                        Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.powerLevel = instance.getPrimaryStat().getValue();
        this.perks = socketsComponent.getSockets()
                .stream()
                .filter(plug -> {
                    if (plug == null || plug.getPlugHash() == null) {
                        return false;
                    }
                    DestinyDefinitionsDestinyInventoryItemDefinition plugDefinition = itemDefinitions.get(plug.getPlugHash());
                    return plugDefinition != null && "Armor Perk".equals(plugDefinition.getItemTypeDisplayName());
                })
                .map(DestinyEntitiesItemsDestinyItemSocketState::getReusablePlugHashes)
                .filter(Objects::nonNull)
                .map(plugHashes -> new PerkChoice(plugHashes, itemDefinitions)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public List<PerkChoice> getPerks() {
        return perks;
    }
}
