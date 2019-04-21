package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemInstanceComponent;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemSocketsComponent;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class ItemInstance implements Comparable {

    private long instanceId;
    private String name;
    private String icon;
    private String tierType;
    private int powerLevel;
    private List<PerkChoice> perks;

    public ItemInstance(long instanceId,
                        DestinyEntitiesItemsDestinyItemInstanceComponent instance,
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent,
                        DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition,
                        Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this.instanceId = instanceId;
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.tierType = itemDefinition.getInventory().getTierTypeName();
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
                .map(socket -> ObjectUtils.firstNonNull(socket.getReusablePlugHashes(), singletonList(socket.getPlugHash())))
                .map(plugHashes -> new PerkChoice(plugHashes, itemDefinitions)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getTierType() {
        return tierType;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public List<PerkChoice> getPerks() {
        return perks;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ItemInstance) {
            return Integer.compare(this.getPowerLevel(), ((ItemInstance) o).getPowerLevel());
        }
        throw new RuntimeException("Illegal argument");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.instanceId, ((ItemInstance) obj).instanceId);
    }
}
