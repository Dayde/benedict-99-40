Vue.component('sweep-result', {
    template: `
<main v-if="error" class="message">
    Please check if everything's right up there. 
</main>
<main v-else-if="loading" class="loading">
</main>
<main v-else class="result">
    <div class="item-filters">
        <div>
            <label v-for="energy in energies" :title="energy.name" v-tippy="{ followCursor: true, touchHold: true }" :for="energy.name">
                <input :id="energy.name" :name="energy.name" type="checkbox" v-model="energyFilter" :value="energy.value">
                <img class="form-img" :src="energy.icon" :alt="energy.name">
            </label>
        </div>
        <div>
            <label v-for="tierType in tierTypes" :title="tierType.name" v-tippy="{ followCursor: true, touchHold: true }" :for="tierType.name">
                <input :id="tierType.name" :name="tierType.name" type="checkbox" v-model="tierTypeFilter" :value="tierType.value">
                <img class="form-img" :src="tierType.icon" :alt="tierType.name">
            </label>
        </div>
    </div>
    <div class="item-containers">
        <item :item="item" :key="item.instanceId" v-for="item in filteredResult" ></item>
    </div>
</main>
`,
    data() {
        return {
            energyFilter: [],
            energies: energies,
            tierTypeFilter: [],
            tierTypes: tierTypes,
            error: false,
            loading: true,
            result: null
        }
    },
    mounted() {
        this.$root.$on('auth', () =>
            this.fetchData(
                this.$route.params.userId,
                this.$route.params.platform,
                this.$route.params.classType,
                this.$route.params.itemCategory
            )
        );
    },
    watch: {
        '$route.params.userId'(newVal) {
            this.debouncedGetAnswer(
                newVal,
                this.$route.params.platform,
                this.$route.params.classType,
                this.$route.params.itemCategory
            )
        },
        '$route.params.platform'(newVal) {
            this.fetchData(
                this.$route.params.userId,
                newVal,
                this.$route.params.classType,
                this.$route.params.itemCategory
            )
        },
        '$route.params.classType'(newVal) {
            this.fetchData(
                this.$route.params.userId,
                this.$route.params.platform,
                newVal,
                this.$route.params.itemCategory
            )
        },
        '$route.params.itemCategory'(newVal) {
            this.fetchData(
                this.$route.params.userId,
                this.$route.params.platform,
                this.$route.params.classType,
                newVal
            )
        }
    },
    created() {
        this.debouncedGetAnswer = _.debounce(this.fetchData, 500)
    },
    methods: {
        fetchData(userId, platform, classType, itemCategory) {
            this.error = false;
            this.loading = true;
            if (this.call) {
                this.call.cancel();
            }
            this.call = axios.CancelToken.source();
            axios
                .get(`/api/users/${this.$route.params.userId}/${this.$route.params.platform}/items`, {
                    params: {
                        classType,
                        itemCategory
                    },
                    cancelToken: this.call.token
                })
                .then(response => {
                    this.loading = false;
                    this.result = enrichResults(response.data);
                    this.result = _.sortBy(response.data, 'totalWeightedStats');
                    this.result = this.result.reverse();
                    this.result = _.sortBy(this.result, 'energyType');
                    this.result = _.sortBy(this.result, 'tierType');
                }, error => {
                    if (axios.isCancel(error)) {
                        // another request was made no worry
                    } else if (error.response.status === 401) {
                        this.$root.$emit('reauth');
                    } else {
                        this.error = true;
                        this.loading = false;
                    }
                });
        }
    },
    computed: {
        filteredResult() {
            return this.result == null ? null :
                this.result.filter(item =>
                    (this.energyFilter.length === 0 || this.energyFilter.indexOf(item.energyType) > -1) &&
                    (this.tierTypeFilter.length === 0 || this.tierTypeFilter.indexOf(item.tierType) > -1)
                );
        }
    }
});

function weight(statName) {
    let weightKey = localStorage.getItem('classType') + "_" + statName;
    let weight = localStorage.getItem(weightKey);
    if (weight === null) {
        weight = stats[statName].defaultWeight;
        localStorage.setItem(weightKey, weight);
    }
    return weight;
}

function computeTotalStats(item) {
    let total = 0;
    let totalWeighted = 0;
    for (let statName in item.stats) {
        total += parseInt(item.stats[statName]);
        totalWeighted += parseInt(item.stats[statName]) * weight(statName);
    }
    item.totalStats = total;
    item.totalWeightedStats = totalWeighted;
}

let enrichResults = function (items) {
    items = _.sortBy(items, item => -parseInt(item.powerLevel));
    let maxPower = items[0].powerLevel;
    for (let item of items) {
        computeTotalStats(item);
        item.maxPower = item.powerLevel === maxPower;
    }
};
