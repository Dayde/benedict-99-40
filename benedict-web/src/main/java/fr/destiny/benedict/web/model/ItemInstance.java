package fr.destiny.benedict.web.model;

import fr.destiny.api.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemInstance {

    private long itemHash;
    private long instanceId;
    private String name;
    private String icon;
    private TierType tierType;
    private String location;

    private int powerLevel;
    private boolean masterwork;
    private int energy;
    private EnergyEnum energyType;

    private Map<StatEnum, Integer> stats;


    public ItemInstance(long instanceId,
                        DestinyEntitiesItemsDestinyItemInstanceComponent instance,
                        DestinyEntitiesItemsDestinyItemSocketsComponent socketsComponent,
                        DestinyEntitiesItemsDestinyItemStatsComponent statsComponent,
                        DestinyDefinitionsDestinyInventoryItemDefinition itemDefinition,
                        String location) {
        this.itemHash = itemDefinition.getHash();
        this.instanceId = instanceId;
        this.name = itemDefinition.getDisplayProperties().getName();
        this.icon = itemDefinition.getDisplayProperties().getIcon();
        this.tierType = TierType.fromString(itemDefinition.getInventory().getTierTypeName());

        this.powerLevel = instance.getPrimaryStat().getValue();

        DestinyEntitiesItemsDestinyItemInstanceEnergy energyType = instance.getEnergy();
        if (energyType != null) {
            this.energyType = EnergyEnum.valueOf(energyType.getEnergyTypeHash());
            this.energy = energyType.getEnergyCapacity();
        } else {
            this.energyType = EnergyEnum.NONE;
        }

        if (this.energy == 10) {
            this.masterwork = true;
        }

        this.stats = new HashMap<>();
        Map<String, DestinyDestinyStat> stats = statsComponent.getStats();
        if (!stats.isEmpty()) {
            for (StatEnum stat : StatEnum.values()) {
                int statValue = stats.get(stat.getHashString()).getValue();
                this.stats.put(stat, statValue);
            }
        }

        if (!stats.isEmpty()) {
            for (DestinyEntitiesItemsDestinyItemSocketState socket : socketsComponent.getSockets()) {
                if (socket == null || socket.getPlugHash() == null || !socket.getIsEnabled()) {
                    continue;
                }

                StatEnum stat = StatEnum.modOf(socket.getPlugHash());
                if (stat != null && this.stats.containsKey(stat)) {
                    this.stats.put(stat, this.stats.get(stat) - stat.modValue(socket.getPlugHash()));
                }
            }
        }

        if (this.masterwork) {
            this.stats.replaceAll((stat, value) -> value - 2);
        }

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

    public TierType getTierType() {
        return tierType;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public boolean isMasterwork() {
        return masterwork;
    }

    public Map<StatEnum, Integer> getStats() {
        return stats;
    }

    public EnergyEnum getEnergyType() {
        return energyType;
    }

    public int getEnergy() {
        return energy;
    }

    public String getLocation() {
        return location;
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
