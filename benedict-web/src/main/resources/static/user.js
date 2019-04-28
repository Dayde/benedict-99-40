Vue.component('user', {
    template: `
<router-link :to="'/sweep/' + user.userId + '/' + user.platform" class="user">
    <i v-if="user.platform === '1'" class="fab fa-xbox"></i>
    <i v-if="user.platform === '2'" class="fab fa-playstation"></i>
    <i v-if="user.platform === '4'" class="fab fa-battle-net"></i>
    {{user.username}}
</router-link>
`,
    props: {
        user: Object
    }
});