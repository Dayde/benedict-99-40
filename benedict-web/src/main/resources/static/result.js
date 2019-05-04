Vue.component('sweep-result', {
    template: `
<main v-if="error" class="message">
    Please check if everything's right up there. 
</main>
<main v-else-if="loading" class="loading">
</main>
<main v-else class="result">
    <div v-if="sort.length" class="title clickable" @click="toggleSort()">
        <i class="fas" :class="{'fa-eye':showSort, 'fa-eye-slash':!showSort}"></i>
        Sort
    </div>
    <div v-else class="title">Nothing left to sort, Tess would be proud !</div>
    <div v-if="showSort" class="item-containers">
        <item :item="item" v-for="item in sort" :key="item.instanceId"></item>
    </div>
    <div class="title clickable" @click="toggleKeep()">
        <i class="fas" :class="{'fa-eye':showKeep, 'fa-eye-slash':!showKeep}"></i>
        Keep
    </div>
    <div v-if="showKeep" class="item-containers">
        <item :item="item" v-for="item in keep" :key="item.instanceId"></item>
    </div>
</main>
`,
    data() {
        return {
            error: false,
            loading: true,
            sort: null,
            keep: null,
            showSort: true,
            showKeep: false
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
                        itemCategory,
                        uncommittedPerkHashes: this.uncommittedPerkHashes()
                    },
                    cancelToken: this.call.token
                })
                .then(response => {
                    this.loading = false;
                    this.sort = response.data.sort;
                    this.keep = response.data.keep;
                }, error => {
                    if (axios.isCancel(error)) {
                        // another request was made no worry
                    } else {
                        this.error = true;
                        this.loading = false;
                    }
                });
        },
        uncommittedPerkHashes() {
            let committedPerksJSON = localStorage.getItem('committedPerks');
            let committedPerks = {};
            if (committedPerksJSON) {
                committedPerks = JSON.parse(committedPerksJSON);
            }
            let uncommittedPerkHashes = [];
            for (let perkHash in committedPerks) {
                if (!committedPerks[perkHash]) {
                    uncommittedPerkHashes.push(perkHash);
                }
            }
            return uncommittedPerkHashes.join(',');
        },
        toggleSort() {
            this.showSort = !this.showSort;
        },
        toggleKeep() {
            this.showKeep = !this.showKeep;
        }
    }
});
