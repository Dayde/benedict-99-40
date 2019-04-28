const Header = {
    template: `
    <div class="header">
        <h2 class="title">Benedict 99-40 helps you sweep your vault</h2>
        <div class="form-container">
            <div>
                <input id="username" name="username" placeholder="Username" required
                       type="text" v-model="username"  spellcheck="false">
            </div>
            
            <div v-if="users" class="users">
                <div v-if="notFound">
                    No mention of that name was found in the archives
                </div>
                <user v-for="user in users" :user="user"></user>
            </div>
            
            <class-type v-model="classType"></class-type>

            <item-category v-model="itemCategory" :classType="classType"></item-category>
        </div>
    </div>
`,
    data() {
        let user = JSON.parse(localStorage.getItem('user'));
        localStorage.setItem('classType', this.$route.params.classType);
        localStorage.setItem('itemCategory', this.$route.params.itemCategory);
        return {
            username: user.username,
            userId: user.userId,
            platform: user.platform,
            classType: localStorage.getItem('classType'),
            itemCategory: localStorage.getItem('itemCategory'),
            users: null
        };
    },
    created() {
        this.debouncedFetchUsers = _.debounce(this.fetchUsers, 500)
    },
    watch: {
        username(newVal) {
            this.debouncedFetchUsers(newVal)
        },
        classType(newVal) {
            localStorage.setItem('classType', newVal);
            this.$router.push({params: {...this.$route.params, classType: newVal}});
        },
        itemCategory(newVal) {
            localStorage.setItem('itemCategory', newVal);
            this.$router.push({params: {...this.$route.params, itemCategory: newVal}});
        }
    },
    methods: {
        fetchUsers(username) {
            this.notFound = false;
            if (this.call) {
                this.call.cancel();
            }
            this.call = axios.CancelToken.source();
            axios
                .get('/api/users', {
                    params: {username},
                    cancelToken: this.call.token
                })
                .then(response => {
                    this.users = response.data;
                    this.notFound = this.users.length === 0;
                });
        }
    }
};