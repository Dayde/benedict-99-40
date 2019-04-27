const Header = {
    template: `
    <div class="header">
        <h2 class="title">Benedict 99-40 helps you sweep your vault</h2>
        <div class="form-container">
            <div>
                <input id="username" name="username" placeholder="Username" required
                       type="text" v-model="username"  spellcheck="false">
            </div>

            <platform v-model="platform"></platform>
            
            <class-type v-model="classType"></class-type>

            <item-category v-model="itemCategory" :classType="classType"></item-category>
        </div>
    </div>
`,
    data() {
        localStorage.setItem('username', this.$route.params.username);
        localStorage.setItem('platform', this.$route.params.platform);
        localStorage.setItem('classType', this.$route.params.classType);
        localStorage.setItem('itemCategory', this.$route.params.itemCategory);
        return {
            username: localStorage.getItem('username'),
            platform: localStorage.getItem('platform'),
            classType: localStorage.getItem('classType'),
            itemCategory: localStorage.getItem('itemCategory')
        };
    },
    watch: {
        username(newVal) {
            localStorage.setItem('username', newVal);
            this.$router.push({params: {...this.$route.params, username: newVal}});
        },
        platform(newVal) {
            localStorage.setItem('platform', newVal);
            this.$router.push({params: {...this.$route.params, platform: newVal}});
        },
        classType(newVal) {
            localStorage.setItem('classType', newVal);
            this.$router.push({params: {...this.$route.params, classType: newVal}});
        },
        itemCategory(newVal) {
            localStorage.setItem('itemCategory', newVal);
            this.$router.push({params: {...this.$route.params, itemCategory: newVal}});
        }
    }
};