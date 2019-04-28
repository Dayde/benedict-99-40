Vue.component('perk-slot', {
    template: `
<div class="container wrap">
    <perk v-for="(perk, index) in perks[armor][armorSlot]" :perk="perk" :key="perk.key" 
    :class="{ 'socket-selected': isCommitted(perk) }" @click.native="toggle(armor, armorSlot, index)"></perk>
</div>
`,
    props: ['name', 'armor', 'armorSlot', 'perks'],
    methods: {
        setPerkKey(perk, committedPerks) {
            perk.key = (committedPerks[perk.hash] ? 'u' : '') + perk.hash;
        },
        isCommitted(perk) {
            let committedPerks = this.getCommittedPerks();
            return !committedPerks.hasOwnProperty(perk.hash) || committedPerks[perk.hash];
        },
        toggle(armor, armorSlot, index) {
            let perk = this.perks[armor][armorSlot][index];
            let committedPerks = this.getCommittedPerks();
            committedPerks[perk.hash] = !this.isCommitted(perk);
            localStorage.setItem('committedPerks', JSON.stringify(committedPerks));
            this.setPerkKey(perk, committedPerks);
            this.$emit('perk-toggled');
        },
        getCommittedPerks() {
            let committedPerksJSON = localStorage.getItem('committedPerks');
            let committedPerks = {};
            if (committedPerksJSON) {
                committedPerks = JSON.parse(committedPerksJSON);
            }
            return committedPerks;
        }
    }
});
