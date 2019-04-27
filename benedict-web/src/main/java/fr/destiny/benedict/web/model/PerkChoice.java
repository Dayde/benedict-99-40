package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.destiny.benedict.web.utils.PerkUtils.GENERIC_PERKS;

public class PerkChoice {

    private long currentPerkHash;
    private List<Perk> choices;

    PerkChoice(Long currentPlugHash, List<Long> reusablePlugHashes, Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this.currentPerkHash = currentPlugHash;
        this.choices = new ArrayList<>();
        reusablePlugHashes.forEach(plugHash -> {
            choices.add(new Perk(itemDefinitions.get(plugHash)));
            if (GENERIC_PERKS.containsKey(plugHash)) {
                GENERIC_PERKS.get(plugHash).forEach(hash ->
                        choices.add(new Perk(itemDefinitions.get(hash), false)));
            }
        });
    }

    public List<Perk> getChoices() {
        return choices;
    }

    public long getCurrentPerkHash() {
        return currentPerkHash;
    }
}
