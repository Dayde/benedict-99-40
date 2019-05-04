Vue.component('item', {
    template: `
<div class="item-container">
    <div class="item-image" :title="item.name" v-tippy="{ followCursor: true, touchHold: true }">
        <img :src="'https://www.bungie.net' + item.icon" :alt="item.name" />
        <img v-if="item.masterwork" src="/masterwork.png" alt="masterwork" :class="{ masterwork: item.masterwork }" />
        <span class="top-right">{{item.powerLevel}}</span>
    </div>
    <div class="item-sockets">
        <perk-choice :perkChoice="perkChoice" v-for="(perkChoice, index) in item.perks" :key="index"></perk-choice>
    </div>
</div>
`,
    props: {
        item: Object
    }
});
