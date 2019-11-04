Vue.component('sweep-result', {
    template: `
<main v-if="error" class="message">
    Please check if everything's right up there. 
</main>
<main v-else-if="loading" class="loading">
</main>
<main v-else class="result">
    <div v-for="(items, extraMod) in result">
        <div class="title">
            <span class="clickable" tabindex="0" @click="toggle(extraMod)" @keyup.space="toggle(extraMod)" @keyup.enter="toggle(extraMod)">
                <i class="fas" :class="{'fa-eye':!hide[extraMod], 'fa-eye-slash':hide[extraMod]}"></i>
                {{ extraMod }}
            </span>
        </div>
        <div v-if="!hide[extraMod]" class="item-containers">
            <item :item="item" v-for="item in items" :key="item.instanceId"></item>
        </div>
    </div>
</main>
`,
    data() {
        return {
            error: false,
            loading: true,
            result: null,
            hide: {}
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
                    this.result = response.data;
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
        },
        toggle(extraMod) {
            Vue.set(this.hide, extraMod, !this.hide[extraMod])
        }
    }
});
