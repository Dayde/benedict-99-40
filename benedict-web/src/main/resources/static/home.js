const Home = {
    template: `
<div class="home">
    <div class="title">Welcome guardian, may I ask your name ?</div>
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
