Vue.component('sweep-result', {
    template: `
<main v-if="error" class="message">
    Please check if everything's right up there. 
</main>
<main v-else-if="loading" class="loading">
</main>
<main v-else class="result">
    <div class="item-containers">
        <item :item="item" :key="item.instanceId" v-for="item in result" ></item>
    </div>
</main>
`,
    data() {
        return {
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
                    this.result = _.sortBy(response.data, 'totalStats');
                    this.result = this.result.reverse();
                    this.result = _.sortBy(this.result, 'energyType');
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
    }
});
