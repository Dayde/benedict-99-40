Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image" :title="item.name">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name" :class="{ masterwork: item.masterwork }" />
        <img v-if="item.masterwork" src="/masterwork.png" :alt="item.name" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">{{item.powerLevel}}</span>
    </div>
    <div class="item-sockets">
        <perk-choice :perkChoice="perkChoice" v-for="perkChoice in item.perks"></perk-choice>
    </div>
</div>
`,
    props: {
        item: Object
    }
});
