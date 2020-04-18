const extraMods = {
    'OUTLAW': {
        name: 'Outlaw',
        value: 'OUTLAW',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/2310924191f6e42e632ed2f2a22cfe58.png'
    },
    'FORGE': {
        name: 'Forge',
        value: 'FORGE',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/cece37b0f41504fdb634484c88883676.png'
    },
    'COLLECTOR': {
        name: 'Collector',
        value: 'COLLECTOR',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/a476d6aa4d42f3f4df05a3298da5ca23.png'
    },
    'SENTRY': {
        name: 'Sentry',
        value: 'SENTRY',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/dddf7d646bb7765abed73f0602e5a01b.png'
    },
    'INVADER': {
        name: 'Invader',
        value: 'INVADER',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/ffaddf1976ccd56af63d68aead7bcd39.png'
    },
    'REAPER': {
        name: 'Reaper',
        value: 'REAPER',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/cc1d90765bb304c4a744b4e9651ede9e.png'
    },
    'OPULENCE': {
        name: 'Opulence',
        value: 'OPULENCE',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/30756d23efef734db35fd58abdde81cb.png'
    },
    'UNDYING': {
        name: 'Undying',
        value: 'UNDYING',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/ab1ac861002da83d3c29186a0b685132.png'
    },
    'DAWN': {
        name: 'Dawn',
        value: 'DAWN',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/81a5c9470746dc2a50974efd3a0ecc54.png'
    },
    'WORTHY': {
        name: 'Worthy',
        value: 'WORTHY',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/d83dfa810bd766e96dae6d94afce3955.png'
    },
    'NONE': {
        name: 'None',
        value: 'NONE',
        icon: 'https://www.bungie.net/img/misc/missing_icon_d2.png'
    },
};

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
        icon: 'https://www.bungie.net/common/destiny2_content/icons/DestinyEnergyTypeDefinition_9fbcfcef99f4e8a40d8762ccb556fcd4.png'
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

const stats = {
    MOBILITY: {
        name: 'Mobility',
        value: 'MOBILITY',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/c9aa8439fc71c9ee336ba713535569ad.png',
        weight: 3 / 5
    },
    RESILIENCE: {
        name: 'Resilience',
        value: 'RESILIENCE',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/9f5f65d08b24defb660cebdfd7bae727.png',
        weight: 3 / 5
    },
    RECOVERY: {
        name: 'Recovery',
        value: 'RECOVERY',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/47e16a27c8387243dcf9b5d94e26ccc4.png',
        weight: 4 / 5
    },
    DISCIPLINE: {
        name: 'Discipline',
        value: 'DISCIPLINE',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/ca62128071dc254fe75891211b98b237.png',
        weight: 3 / 5
    },
    INTELLECT: {
        name: 'Intellect',
        value: 'INTELLECT',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/59732534ce7060dba681d1ba84c055a6.png',
        weight: 1
    },
    STRENGTH: {
        name: 'Strength',
        value: 'STRENGTH',
        icon: 'https://www.bungie.net/common/destiny2_content/icons/c7eefc8abbaa586eeab79e962a79d6ad.png',
        weight: 3 / 5
    },
};

Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image clickable"
         v-tippy="{ html: '#item' + item.instanceId, trigger: 'click', interactive: true, reactive: true, hideOnClick: true }">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name"/>
        <img v-if="item.masterwork" src="/masterwork.png" alt="masterwork" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">
            {{item.powerLevel}}
        </span>
    </div>
    <div class="item-details">
        <span class="item-mod">
            <img :src="extraMod(item.extraMod).icon"
                 class="extra-mod-icon"/>
        </span>
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
            <span :class="totalStatClass(item.totalWeightedStats)">{{ item.totalWeightedStats | formatNumber }}</span>
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
            extraMods: extraMods
        };
    },
    methods: {
        classType(classType) {
            return this.classes[classType];
        },
        energyType(energyType) {
            return this.energies[energyType];
        },
        extraMod(extraMod) {
            return this.extraMods[extraMod];
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
