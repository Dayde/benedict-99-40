Vue.component('perk', {
    template: `
<div class="socket-container" :title="perk.name">
    <img :src="'https://www.bungie.net' + perk.icon" :alt="perk.name"/>
</div>
`,
    props: {
        perk: Object
    }
});
