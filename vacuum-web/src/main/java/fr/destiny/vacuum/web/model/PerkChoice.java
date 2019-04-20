package fr.destiny.vacuum.web.model;

import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class PerkChoice {

    private List<Perk> choices;


    public List<Perk> getChoices() {
        return choices;
    }

    PerkChoice(List<Long> reusablePlugHashes, Map<Long, DestinyDefinitionsDestinyInventoryItemDefinition> itemDefinitions) {
        this.choices = new ArrayList<>();
        reusablePlugHashes.forEach(plugHash -> choices.add(new Perk(itemDefinitions.get(plugHash))));
    }


}
