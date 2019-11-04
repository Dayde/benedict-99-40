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

Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image clickable"
         v-tippy="{ html: '#item' + item.instanceId, trigger: 'click', interactive: true, reactive: true, hideOnClick: true }">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name"/>
        <img v-if="item.masterwork" src="/masterwork.png" alt="masterwork" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">
            <img :src="energyType(item.energyType).icon"
                 class="energy-type"/>
            {{item.powerLevel}}
        </span>
    </div>
    <div class="item-stats">
       <div>{{ item.stats.MOBILITY }}</div>
       <div>{{ item.stats.RESILIENCE }}</div>
       <div>{{ item.stats.RECOVERY }}</div>
       <div>{{ item.stats.DISCILPLINE }}</div>
       <div>{{ item.stats.INTELLECT }}</div>
       <div>{{ item.stats.STRENGTH }}</div>
       <div>---</div>
       <div>{{ item.totalStats }}</div>
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
            energies: energies
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
        }
    }
});
