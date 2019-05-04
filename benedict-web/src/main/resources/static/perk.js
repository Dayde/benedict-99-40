Vue.component('perk', {
    template: `
<div class="socket-container" :title="perk.name" v-if="perk.primary" v-tippy="{ followCursor: true, touchHold: true }">
    <img :src="'https://www.bungie.net' + perk.icon" :alt="perk.name"/>
</div>
`,
    props: {
        perk: Object
    }
});
