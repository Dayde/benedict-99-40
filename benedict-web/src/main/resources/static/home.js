const Home = {
    template: `
<div class="home container-column">
    <div class="title">Welcome guardian, I'd be glad to help you sweep your vault.</div>
    <div class="title">Will you let me in ?</div>
    <a href="/login"><i class="fas fa-sign-in-alt"></i></a>
</div>
`,
    created() {
        if (this.$route.query.code) {
            axios.get('/token', {params: {code: this.$route.query.code}})
                .then(response => {
                    let token = response.data;
                    token.timestamp = new Date().getTime();
                    localStorage.setItem('token', JSON.stringify(token));
                    axios.defaults.params = {};
                    axios.defaults.params['token'] = token.access_token;

                    axios.get('/api/user')
                        .then(response => {
                            let user = response.data;
                            this.$router.push({
                                path: `/sweep` +
                                    `/${user.userId}` +
                                    `/${user.platform}`
                            });
                        });
                })
        } else if (localStorage.getItem('token') && localStorage.getItem('user')) {
            axios.defaults.params = {};
            axios.defaults.params['token'] = JSON.parse(localStorage.getItem('token')).access_token;
            let user = JSON.parse(localStorage.getItem('user'));
            this.$router.push({
                path: `/sweep` +
                    `/${user.userId}` +
                    `/${user.platform}` +
                    `/${localStorage.getItem('classType')}` +
                    `/${localStorage.getItem('itemCategory')}`
            });
        }
    }
};
