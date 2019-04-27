Vue.component('perk-choice', {
    template: `
<div>
    <div class="item-socket" v-for="perk in perkChoice.choices">
        <perk :perk="perk" :class="{'socket-selected': perk.hash===perkChoice.currentPerkHash}"></perk>
    </div>
</div>
`,
    props: {
        perkChoice: Object
    }
});
