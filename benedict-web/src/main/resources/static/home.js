const Home = {
    template: `
<div class="home">
    <h1>Welcome guardian, may I ask your name ?</h1>
    <div>
        <input type="text" v-model="username" placeholder="Username" spellcheck="false">
    </div>
    <div class="players">
        <div v-if="notFound">
            No mention of that name was found in the archives
        </div>
        <router-link :to="'/' + user.username + '/' + user.platform" v-for="user in users" class="player">
            <i v-if="user.platform === 1" class="fab fa-xbox"></i>
            <i v-if="user.platform === 2" class="fab fa-playstation"></i>
            <i v-if="user.platform === 4" class="fab fa-battle-net"></i>
            {{user.username}}
        </router-link>
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
                this.debouncedFetchPlayers(newVal);
            } else {
                this.users = [];
            }
        }
    },
    created() {
        let username = localStorage.getItem('username');
        let platform = localStorage.getItem('platform');
        if (username && platform) {
            this.$router.push({path: `/sweep/${username}/${platform}`});
        }
        this.debouncedFetchPlayers = _.debounce(this.fetchPlayers, 500)
    },
    methods: {
        fetchPlayers(username) {
            this.notFound = false;
            axios
                .get('/api/player', {params: {username}})
                .then(response => {
                    this.users = response.data;
                    this.notFound = this.users.length === 0;
                });
        }
    }
};
