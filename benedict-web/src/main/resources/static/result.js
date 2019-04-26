const Result = {
    template: `
<div v-if="loading" class="loading fa-9x">
  <i class="fas fa-sync fa-spin"></i>
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
            loading: true,
            sort: null,
            keep: null
        }
    },
    mounted() {
        this.loading = true;
        let url = 'http://localhost:3000/api/items?';
        url += 'username=' + this.$route.params.username;
        url += '&platform=' + this.$route.params.platform;
        url += '&classType=' + this.$route.params.classType;
        url += '&itemCategory=' + this.$route.params.itemCategory;
        axios
            .get(url)
            .then(response => {
                this.loading = false;
                this.sort = response.data.sort;
                this.keep = response.data.keep;
            })
    },
    watch: {
        '$route'(newVal) {
            this.loading = true;
            let url = 'http://localhost:3000/api/items?';
            url += 'username=' + newVal.params.username;
            url += '&platform=' + newVal.params.platform;
            url += '&classType=' + newVal.params.classType;
            url += '&itemCategory=' + newVal.params.itemCategory;
            axios
                .get(url)
                .then(response => {
                    this.loading = false;
                    this.sort = response.data.sort;
                    this.keep = response.data.keep;
                })
        }
    }
};