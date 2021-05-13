const energies = {
    SOLAR: {
        name: 'Solar',
        value: 'SOLAR',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/DestinyEnergyTypeDefinition_2a1773e10968f2d088b97c22b22bba9e.png'
    },
    VOID: {
        name: 'Void',
        value: 'VOID',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/DestinyEnergyTypeDefinition_ceb2f6197dccf3958bb31cc783eb97a0.png'
    },
    ARC: {
        name: 'Arc',
        value: 'ARC',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/DestinyEnergyTypeDefinition_092d066688b879c807c3b460afdd61e6.png'
    }
};

const tierTypes = {
    LEGENDARY: {
        name: 'Legendary',
        value: 'LEGENDARY',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/7eea47cc31d9b065213f85169e668b6e.png'
    },
    EXOTIC: {
        name: 'Exotic',
        value: 'EXOTIC',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/ee21b5bc72f9e48366c9addff163a187.png'
    }
};

Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image clickable"
         v-tippy="{ html: '#item' + item.instanceId, trigger: 'click', interactive: true, reactive: true, hideOnClick: true }">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name"/>
        <img v-if="item.masterwork" src="/masterwork.png" alt="masterwork" :class="{ masterwork: item.masterwork }" />
        <span class="top-right" :class="{ maxpower: item.maxPower }">
            {{item.powerLevel}}
        </span>
    </div>
    <div class="item-details">
        <span class="item-energy">
            <img :src="energyType(item.energyType).icon"
                 class="stat-icon"/>
            &nbsp;&nbsp;{{ item.energy }}
        </span>
        <div class="item-stats" v-if="item.totalStats != 0">
            <span v-for="(stat, statName) in stats" :class="statClass(item.stats[statName])">
                <img :src="stats[statName].icon"
                     class="stat-icon"/>
                {{ ( item.stats[statName] < 10 ? '&nbsp;&nbsp;' : '' ) + item.stats[statName] }}
            </span>
            <span>------</span>
            <span :class="totalStatClass(item.totalWeightedStats)">{{ item.totalWeightedStats | formatNumber }} / {{ item.totalStats }}</span>
        </div>
    </div>
    <div :id="'item' + item.instanceId">
        <div>{{ item.name }}</div>
        <img  v-for="character in characters" class="location clickable"
              :class="{current: item.location === character.characterId}" 
              :src="classType(character.classType).icon" :alt="classType(character.classType).name"
              @click="transferItem(character.characterId)"/>
        <img src="https://bungie.net/common/destiny2_content/icons/42284fb3e73118f37cc7563c6ae70097.png" alt="Vault"
             class="location clickable" :class="{current: item.location === ''}" @click="transferItem('')"/>
    </div>
</div>
`,
    props: {
        item: Object
    },
    data() {
        let user = JSON.parse(localStorage.getItem('user'));
        return {
            characters: user.characters,
            classes: classes,
            energies: energies,
            stats: stats,
        };
    },
    methods: {
        classType(classType) {
            return this.classes[classType];
        },
        energyType(energyType) {
            return this.energies[energyType];
        },
        transferItem(characterId) {
            let user = JSON.parse(localStorage.getItem('user'));
            axios.put(
                `/api/users/${user.userId}/${user.platform}/items/${this.item.itemHash}/${this.item.instanceId}`,
                {},
                {
                    params: {
                        currentLocation: this.item.location,
                        targetLocation: characterId
                    }
                }
            ).then(response => this.item.location = characterId);
        },
        statClass(value) {
            return value >= 20 ? 'high' : (value >= 10 ? 'medium' : 'low');
        },
        totalStatClass(value) {
            return value >= 40 ? 'high' : (value >= 35 ? 'medium' : 'low');
        }
    }
});

Vue.filter("formatNumber", function (value) {
    return value.toFixed(1);
});
