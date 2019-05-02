Vue.component('auth', {
    template: `
<div class="username">
    {{username}}  <i class="fa fa-sign-out-alt" @click="logout()"></i>
</div>
`,
    data() {
        return {
            authenticated: false,
            username: JSON.parse(localStorage.user).username
        }
    },
    mounted() {
        if (localStorage.token) {
            let token = JSON.parse(localStorage.token);
            if (((token.timestamp || 0) + token.expires_in) < new Date().getTime()) {
                this.refreshToken(token.refresh_token);
            } else {
                axios.defaults.params = {};
                axios.defaults.params['token'] = token.access_token;
            }
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
                    localStorage.token = JSON.stringify(token);
                    axios.defaults.params = {};
                    axios.defaults.params['token'] = token.access_token;
                    this.$router.push('/');
                });
        }
    }
});
