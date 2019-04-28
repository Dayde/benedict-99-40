const Home = {
    template: `
<div class="home">
    <h1>Welcome guardian, may I ask your name ?</h1>
    <div>
        <input type="text" v-model="username" placeholder="Username" spellcheck="false">
    </div>
    <div class="users">
        <div v-if="notFound">
            No mention of that name was found in the archives
        </div>
        <user v-for="user in users" :user="user"></user>
    </div>
</div>
`,
    data() {
        return {
            username: '',
            users: [],
            notFound: false
        }
    },
    watch: {
        username(newVal) {
            if (newVal) {
                this.debouncedFetchUsers(newVal);
            } else {
                this.users = [];
            }
        }
    },
    created() {
        let userJSON = localStorage.getItem('user');
        if (userJSON) {
            let user = JSON.parse(userJSON);
            this.$router.push({path: `/sweep/${user.userId}/${user.platform}`});
        } else {
            localStorage.clear();
            this.$router.push({path: '/'});
        }
        this.debouncedFetchUsers = _.debounce(this.fetchUsers, 500)
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
};
