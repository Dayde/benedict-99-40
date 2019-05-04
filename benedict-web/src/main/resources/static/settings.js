let sortedArmors = [
    'HELMET',
    'GAUNTLETS',
    'CHEST_ARMOR',
    'LEG_ARMOR',
    'CLASS_ARMOR'
];

const Settings = {
    template: `
<div class="container-column overflow">
    <header>
        <div class="title">Here you can be a little more specific about what requires sweeping.</div>
        <div class="title">Select perks you care about</div>
    </header>
    <main>
        <div class="container-column">
            <div class="container" v-for="armor in sortedArmors">
                <armor :armor="armor"></armor>
                <div class="container-column">
                    <perk-slot v-for="(slotPerks, armorSlot) in perks[armor]" :key="armorSlot"
                      :armor="armor"
                      :armorSlot="armorSlot"
                      :perks="perks"
                      @perk-toggled="refresh()"></perk-slot>
                </div>
            </div>
        </div>
    </main>
</div>
`,
    data() {
        return {
            perks: {},
            sortedArmors: sortedArmors
        };
    },
    mounted() {
        this.fetchPerks()
    },
    methods: {
        fetchPerks() {
            axios
                .get('/api/perks')
                .then(response => {
                    this.perks = response.data;
                    let committedPerks = this.getCommittedPerks();
                    for (let armor in this.perks) {
                        for (let armorSlot in this.perks[armor]) {
                            for (let perk of this.perks[armor][armorSlot]) {
                                this.setPerkKey(perk, committedPerks);
                            }
                        }
                    }
                });
        },
        setPerkKey(perk, committedPerks) {
            perk.key = (committedPerks[perk.hash] ? 'u' : '') + perk.hash;
        },
        getCommittedPerks() {
            let committedPerksJSON = localStorage.getItem('committedPerks');
            let committedPerks = {};
            if (committedPerksJSON) {
                committedPerks = JSON.parse(committedPerksJSON);
            }
            return committedPerks;
        },
        refresh() {
            for (let armor in this.perks) {
                for (let armorSlot in this.perks[armor]) {
                    this.perks[armor][armorSlot] = this.perks[armor][armorSlot].slice();
                }
            }
        }
    }
};
