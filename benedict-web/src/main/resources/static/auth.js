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
            axios.defaults.params = {};
            axios.defaults.params['token'] = JSON.parse(localStorage.token).access_token;
        }
    },
    methods: {
        logout() {
            localStorage.clear();
            this.$router.push({path: '/'});
        }
    }
});