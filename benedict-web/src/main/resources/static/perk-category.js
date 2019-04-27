Vue.component('perk-category', {
    template: `
        <div>
            <span>{{name}}</span>
            <perk v-for="(perk, index) in perks[category]" :perk="perk" :key="perk.key" 
            :class="{ 'socket-selected': isCommitted(perk) }" @click.native="toggle(category, index)"></perk>
        </div>
`,
    props: ['name', 'category', 'perks'],
    methods: {
        setPerkKey(perk, committedPerks) {
            perk.key = (committedPerks[perk.hash] ? 'u' : '') + perk.hash;
        },
        isCommitted(perk) {
            let committedPerks = this.getCommittedPerks();
            return !committedPerks.hasOwnProperty(perk.hash) || committedPerks[perk.hash];
        },
        toggle(category, index) {
            let perk = this.perks[category][index];
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
