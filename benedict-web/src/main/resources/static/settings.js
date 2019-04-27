const Settings = {
    template: `
<div class="container-column"">
    <h2 class="title">Here you can be a little more specific about what requires sweeping.</h2>
    <h3 class="title">Salect perks you care about</h3>
    <div>
        <h3 class="title">Helmet</h3>
        <perk-category name="First Slot" category="helmetFirstPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
        <perk-category name="Second Slot" category="helmetSecondPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
    </div>
    <div>
        <h3 class="title">Gauntlets</h3>
        <perk-category name="First Slot" category="gauntletsFirstPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
        <perk-category name="Second Slot" category="gauntletsSecondPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
    </div>
    <div>
        <h3 class="title">Chest Armor</h3>
        <perk-category name="First Slot" category="chestArmorFirstPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
        <perk-category name="Second Slot" category="chestArmorSecondPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
    </div>
    <div>
        <h3 class="title">Leg Armor</h3>
        <perk-category name="First Slot" category="legArmorFirstPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
        <perk-category name="Second Slot" category="legArmorSecondPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
    </div>
    <div>
        <h3 class="title">Class Armor</h3>
        <perk-category name="First Slot" category="classArmorFirstPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
        <perk-category name="Second Slot" category="classArmorSecondPerks" :perks="perks" @perk-toggled="refresh()"></perk-category>
    </div>
</div>
`,
    data() {
        return {
            perks: {}
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
                    for (let category in this.perks) {
                        for (let perk of this.perks[category]) {
                            this.setPerkKey(perk, committedPerks);
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
            for (let key in this.perks) {
                this.perks[key] = this.perks[key].slice();
            }
        }
    }
};
