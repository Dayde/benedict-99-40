const Result = {
    template: `
<div v-if="error" class="loading">
    Please check if everything's right up there. 
</div>
<div v-else-if="loading" class="loading">
  <img src="/benedict.gif" alt="Sweep sweep sweep...">
</div>
<div v-else class="result">
    <h1>Sort</h1>
    <div class="item-containers">
        <item :item="item" v-for="item in sort"></item>
    </div>
    <h1>Keep</h1>
    <div class="item-containers">
        <item :item="item" v-for="item in keep"></item>
    </div>
</div>
`,
    data() {
        return {
            error: false,
            loading: true,
            sort: null,
            keep: null
        }
    },
    mounted() {
        this.fetchData(
            this.$route.params.username,
            this.$route.params.platform,
            this.$route.params.classType,
            this.$route.params.itemCategory
        )
    },
    watch: {
        '$route.params.username'(newVal) {
            this.debouncedGetAnswer(
                newVal,
                this.$route.params.platform,
                this.$route.params.classType,
                this.$route.params.itemCategory
            )
        },
        '$route.params.platform'(newVal) {
            this.fetchData(
                this.$route.params.username,
                newVal,
                this.$route.params.classType,
                this.$route.params.itemCategory
            )
        },
        '$route.params.classType'(newVal) {
            this.fetchData(
                this.$route.params.username,
                this.$route.params.platform,
                newVal,
                this.$route.params.itemCategory
            )
        },
        '$route.params.itemCategory'(newVal) {
            this.fetchData(
                this.$route.params.username,
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
        fetchData(username, platform, classType, itemCategory) {
            this.error = false;
            this.loading = true;
            if (this.call) {
                this.call.cancel();
            }
            this.call = axios.CancelToken.source();
            axios
                .get('/api/items', {
                    params: {
                        username,
                        platform,
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
        }
    }
};