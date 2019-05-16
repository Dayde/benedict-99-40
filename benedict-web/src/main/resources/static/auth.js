Vue.component('auth', {
    template: `
<div class="username">
    {{username}}
    <i class="fa fa-sign-out-alt clickable" tabindex="0" @click="logout"
       @keyup.space="logout" @keyup.enter="logout"></i>
</div>
`,
    data() {
        return {
            authenticated: false,
            username: JSON.parse(localStorage.getItem('user')).username
        }
    },
    mounted() {
        if (localStorage.getItem('token')) {
            let token = JSON.parse(localStorage.getItem('token'));
            axios.get('/api/user', {params: {token: token.access_token}})
                .then(() => {
                    axios.defaults.params = {};
                    axios.defaults.params['token'] = token.access_token;
                    this.$root.$emit('auth');
                })
                .catch(() => {
                    this.refreshToken(token.refresh_token)
                });
            this.$root.$on('reauth', () =>
                this.refreshToken(token.refresh_token)
            );
        }
    },
    methods: {
        logout() {
            localStorage.clear();
            this.$router.push({path: '/'});
        },
        refreshToken(refreshToken) {
            axios.get('/token/refresh', {params: {refreshToken}})
                .then(response => {
                    let token = response.data;
                    token.timestamp = new Date().getTime();
                    localStorage.setItem('token', JSON.stringify(token));
                    axios.defaults.params = {};
                    axios.defaults.params['token'] = token.access_token;
                    this.$router.push('/');
                });
        }
    }
});
