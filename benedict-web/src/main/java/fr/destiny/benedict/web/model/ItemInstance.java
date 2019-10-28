package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemInstanceComponent;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemSocketsComponent;
import fr.destiny.api.model.DestinyEntitiesItemsDestinyItemStatsComponent;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

public class ItemInstance implements Comparable {

    private long itemHash;
    private long instanceId;
    private String name;
    private String icon;
    private String tierType;
    private int powerLevel;
    private boolean masterwork;
    private String location;

    private int mobility;
    private int resilience;
    private int recovery;
    private int discilpline;
    private int intellect;
    private int strength;

    private int totalStats;


    public ItemInstance(long instanceId,
                        DestinyEntitiesItemsDestinyItemInstanceComponent instance,
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent,
                        DestinyEntitiesItemsDestinyItemStatsComponent statsComponent, DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition,
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
        mobility = statsComponent.getStats().get(StatEnum.MOBILITY.getHash()).getValue();
        resilience = statsComponent.getStats().get(StatEnum.RESILIENCE.getHash()).getValue();
        recovery = statsComponent.getStats().get(StatEnum.RECOVERY.getHash()).getValue();
        discilpline = statsComponent.getStats().get(StatEnum.DISCILPLINE.getHash()).getValue();
        intellect = statsComponent.getStats().get(StatEnum.INTELLECT.getHash()).getValue();
        strength = statsComponent.getStats().get(StatEnum.STRENGTH.getHash()).getValue();
        totalStats = mobility + resilience + recovery + discilpline + intellect + strength;
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

    public String getLocation() {
        return location;
    }

    public int getMobility() {
        return mobility;
    }

    public int getResilience() {
        return resilience;
    }

    public int getRecovery() {
        return recovery;
    }

    public int getDiscilpline() {
        return discilpline;
    }

    public int getIntellect() {
        return intellect;
    }

    public int getStrength() {
        return strength;
    }

    public int getTotalStats() {
        return totalStats;
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
