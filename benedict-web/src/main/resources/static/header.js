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
        return {
            username: this.$route.params.username,
            platform: this.$route.params.platform,
            classType: this.$route.params.classType,
            itemCategory: this.$route.params.itemCategory
        };
    },
    watch: {
        username(newVal) {
            this.$router.push({params: {...this.$route.params, username: newVal}});
        },
        platform(newVal) {
            this.$router.push({params: {...this.$route.params, platform: newVal}});
        },
        classType(newVal) {
            this.$router.push({params: {...this.$route.params, classType: newVal}});
        },
        itemCategory(newVal) {
            this.$router.push({params: {...this.$route.params, itemCategory: newVal}});
        }
    }
};