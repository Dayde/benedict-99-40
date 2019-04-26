Vue.component('perk-choice', {
    template: `
<div class="item-socket">
    <perk :perk="perk" v-for="perk in perks"></perk>
</div>
`,
    props: {
        perks: Array
    }
});
