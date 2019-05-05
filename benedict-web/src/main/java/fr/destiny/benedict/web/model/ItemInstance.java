package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemInstanceComponent;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemSocketsComponent;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class ItemInstance implements Comparable {

    private long itemHash;
    private long instanceId;
    private String name;
    private String icon;
    private String tierType;
    private int powerLevel;
    private boolean masterwork;
    private List<PerkChoice> perks;
    private String location;


    public ItemInstance(long instanceId,
                        DestinyEntitiesItemsDestinyItemInstanceComponent instance,
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent,
                        DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition,
                        Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions,
                        String location) {
        this.itemHash = itemDefinition.getHash();
        this.instanceId = instanceId;
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.tierType = itemDefinition.getInventory().getTierTypeName();
        this.powerLevel = instance.getPrimaryStat().getValue();
        this.masterwork = socketsComponent.getSockets()
                .stream()
                .anyMatch(
                        plug -> plug != null
                                && plug.getPlugHash() != null
                                && itemDefinitions.get(plug.getPlugHash())
                                .getDisplayProperties()
                                .getName()
                                .equals("Masterwork")
                );
        this.perks = socketsComponent.getSockets()
                .stream()
                .filter(plug -> {
                    if (plug == null || plug.getPlugHash() == null) {
                        return false;
                    }
                    DestinyDefinitionsDestinyInventoryItemDefinition plugDefinition = itemDefinitions.get(plug.getPlugHash());
                    return plugDefinition != null && "Armor Perk".equals(plugDefinition.getItemTypeDisplayName());
                })
                .map(socket -> {
                    List<Long> plugHashes = socket.getReusablePlugHashes();
                    if (plugHashes == null) {
                        plugHashes = singletonList(socket.getPlugHash());
                    }
                    return new PerkChoice(socket.getPlugHash(), plugHashes, itemDefinitions);
                })
                .collect(Collectors.toList());
        this.location = location;
    }

    public long getItemHash() {
        return itemHash;
    }

    public long getInstanceId() {
        return instanceId;
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

    public boolean isMasterwork() {
        return masterwork;
    }

    public List<PerkChoice> getPerks() {
        return perks;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof ItemInstance) {
            return Integer.compare(this.powerLevel, ((ItemInstance) o).powerLevel);
        }
        throw new RuntimeException("Illegal argument");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemInstance && Objects.equals(this.instanceId, ((ItemInstance) obj).instanceId);
    }
}
