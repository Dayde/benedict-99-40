const Header = {
    template: `
    <div class="header" :style="style">
        <h2 class="title">Benedict 99-40 helps you sweep your vault</h2>
        <div class="form-container">
            <user-select :username="username"></user-select>
            
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
            user: user,
            classType: localStorage.getItem('classType'),
            itemCategory: localStorage.getItem('itemCategory'),
            style: ''
        };
    },
    created() {
        this.updateBackgroundImage()
    },
    watch: {
        classType(newVal) {
            localStorage.setItem('classType', newVal);
            this.$router.push({params: {...this.$route.params, classType: newVal}});
            this.updateBackgroundImage();
        },
        itemCategory(newVal) {
            localStorage.setItem('itemCategory', newVal);
            this.$router.push({params: {...this.$route.params, itemCategory: newVal}});
        }
    },
    methods: {
        currentCharater() {
            let characters = this.user.characters;
            for (let id in characters) {
                let currentCharacter = characters.hasOwnProperty(id) && characters[id];
                if (this.classType === currentCharacter.classType) {
                    return currentCharacter;
                }
            }
        },
        updateBackgroundImage() {
            this.style = `background-image: url(https://bungie.net/${this.currentCharater().emblemBackground});`;
        }
    }
};