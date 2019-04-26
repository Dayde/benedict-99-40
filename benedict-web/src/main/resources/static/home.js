const Home = {
    template: `
<div>
    <p>
        <span>Hi there :) what's your name ?</span>
        <input v-model="username">  
    </p>
    <router-link :to="'/' + username">Confirm</router-link>
</div>
`,
    data() {
        return {
            username: ''
        }
    }
};
