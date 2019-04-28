Vue.component('user-select', {
    template: `
<div class="container-column">
    <input type="text" v-model="mutableUsername" placeholder="Username" spellcheck="false">
    <div class="users-dropdown" v-if="users">
        <div v-if="notFound" class="no-user">
            No mention of that name was found in the archives
        </div>
        <user v-for="user in users" :user="user"></user>
    </div>
</div>
`,
    props: ['username'],
    data() {
        return {
            mutableUsername: this.username,
            users: null,
            notFound: false
        }
    },
    created() {
        this.debouncedFetchUsers = _.debounce(this.fetchUsers, 500)
    },
    watch: {
        mutableUsername(newVal) {
            if (newVal) {
                this.debouncedFetchUsers(newVal);
            } else {
                this.users = null;
            }
        }
    },
    methods: {
        fetchUsers(username) {
            this.notFound = false;
            if (this.call) {
                this.call.cancel();
            }
            this.call = axios.CancelToken.source();
            axios
                .get('/api/users', {
                    params: {username},
                    cancelToken: this.call.token
                })
                .then(response => {
                    this.users = response.data;
                    this.notFound = this.users.length === 0;
                });
        }
    }
});
