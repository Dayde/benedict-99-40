Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image clickable"
         v-tippy="{ html: '#item' + item.instanceId, trigger: 'click', interactive: true, reactive: true, hideOnClick: true }">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name"/>
        <img v-if="item.masterwork" src="/masterwork.png" alt="masterwork" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">{{item.powerLevel}}</span>
    </div>
    <div class="item-sockets">
        <perk-choice :perkChoice="perkChoice" v-for="(perkChoice, index) in item.perks" :key="index"></perk-choice>
    </div>
    <div :id="'item' + item.instanceId">
        <img  v-for="character in characters" class="clickable" 
              :src="classType(character.classType).icon" :alt="classType(character.classType).name"
              @click="transferItem(character.characterId)"/>
        <img src="https://bungie.net/common/destiny2_content/icons/42284fb3e73118f37cc7563c6ae70097.png" alt="Vault"
             class="clickable" @click="transferItem('')"/>
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
            classes: classes
        };
    },
    methods: {
        classType(classType) {
            return this.classes[classType];
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
