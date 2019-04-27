Vue.component('class-type', {
    template: `
<div>
    <label>
        <input id="titan" name="classType" type="radio" v-model="mutableClassType" value="TITAN">
        <img class="form-img"
             src="https://bungie.net/common/destiny2_content/icons/8956751663b4394cd41076f93d2dd0d6.png">
    </label>
    <label>
        <input id="hunter" name="classType" type="radio" v-model="mutableClassType" value="HUNTER">
        <img class="form-img"
             src="https://bungie.net/common/destiny2_content/icons/e7324e8c29c5314b8bce166ff167859d.png">
    </label>
    <label>
        <input id="warlock" name="classType" type="radio" v-model="mutableClassType" value="WARLOCK">
        <img class="form-img"
             src="https://bungie.net/common/destiny2_content/icons/bf7b2848d2f5fbebbf350d418b8ec148.png">
    </label>
</div>
`,
    props: ['value'],
    data() {
        return {
            mutableClassType: this.value
        }
    },
    watch: {
        mutableClassType(newVal) {
            this.$emit('input', newVal);
        }
    }
});

const ClassType = {
    template: '<class-type v-model="classType" class="choose-class"></class-type>',
    data() {
        return {
            classType: null
        }
    },
    created() {
        let itemCategory = localStorage.getItem('classType');
        if (itemCategory) {
            this.$router.push({
                path: `/sweep` +
                    `/${localStorage.getItem('username')}` +
                    `/${localStorage.getItem('platform')}` +
                    `/${localStorage.getItem('classType')}` +
                    `/${itemCategory}`
            });
        }
    },
    watch: {
        classType(newVal) {
            let username = this.$route.params.username;
            let platform = this.$route.params.platform;
            this.$router.push({
                path: `/${username}/${platform}/${newVal}/ARMOR`
            });
        }
    }
};
