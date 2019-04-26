Vue.component('platform', {
    template: `
<div>
    <label>
        <input name="platform" type="radio" v-model="mutablePlatform" value="1">
        <i class="fab fa-xbox platform-icon"></i>
    </label>
    <label>
        <input name="platform" type="radio" v-model="mutablePlatform" value="2">
        <i class="fab fa-playstation  platform-icon"></i>
    </label>
    <label>
        <input name="platform" type="radio" v-model="mutablePlatform" value="4">
        <i class="fab fa-battle-net platform-icon"></i>
    </label>
</div>
`,
    props: ['value'],
    data() {
        return {
            mutablePlatform: this.value
        }
    },
    watch: {
        mutablePlatform(newVal) {
            this.$emit('input', newVal);
        }
    }
});

const Platform = {
    template: '<platform v-model="platform"></platform>',
    data() {
        return {
            platform: null
        }
    },
    watch: {
        platform(newVal) {
            let username = this.$route.params.username;
            this.$router.push({path: `/${username}/${newVal}`});
        }
    }
};
