Vue.component('perk-slot', {
    template: `
<div class="container perk-slot">
    <i class="fas fa-check-circle clickable" tabindex="0" @click="setAll(armor, armorSlot, true)"
       @keyup.space="setAll(armor, armorSlot, true)" @keyup.enter="setAll(armor, armorSlot, true)"></i>
    <i class="fas fa-times-circle clickable" tabindex="0" @click="setAll(armor, armorSlot, false)"
       @keyup.space="setAll(armor, armorSlot, false)" @keyup.enter="setAll(armor, armorSlot, false)"></i>
    <div class="wrap">
        <perk v-for="(perk, index) in perks[armor][armorSlot]" :perk="perk" :key="perk.key" class="clickable"
        :class="{ 'socket-selected': isCommitted(perk) }" tabindex="0" @click.native="toggle(armor, armorSlot, index)"
        @keyup.space.native="toggle(armor, armorSlot, index)" @keyup.enter.native="toggle(armor, armorSlot, index)"></perk>
    </div>
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
        setAll(armor, armorSlot, committed) {
            let perks = this.perks[armor][armorSlot];
            let committedPerks = this.getCommittedPerks();
            for (let perk of perks) {
                committedPerks[perk.hash] = committed;
                this.setPerkKey(perk, committedPerks);
            }
            localStorage.setItem('committedPerks', JSON.stringify(committedPerks));
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
