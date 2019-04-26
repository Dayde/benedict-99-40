Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image" :title="item.name">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">{{item.powerLevel}}</span>
    </div>
    <div class="item-sockets">
        <div v-for="perks in item.perks">
            <perk-choice :perks="choice" v-for="choice in perks"></perk-choice>
        </div>
    </div>
</div>
`,
    props: {
        item: Object
    }
});
