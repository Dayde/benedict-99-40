const Home = {
    template: `
<div class="home">
    <h1>Welcome guardian, may I ask your name ?</h1>
    <user-select></user-select>
</div>
`,
    created() {
        let userJSON = localStorage.getItem('user');
        if (userJSON) {
            let user = JSON.parse(userJSON);
            this.$router.push({path: `/sweep/${user.userId}/${user.platform}`});
        } else {
            localStorage.clear();
            this.$router.push({path: '/'});
        }
    }
};
