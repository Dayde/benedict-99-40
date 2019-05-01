const Header = {
    template: `
<div class="header" :style="style">
    <img :src="emblemBackgroundUrl">
    <div class="title">Benedict 99-40 helps you sweep your vault</div>
    <div class="form-container">
        <class-type v-model="classType"></class-type>

        <item-category v-model="itemCategory" :classType="classType"></item-category>
        
        <auth></auth>
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
            style: '',
            emblemBackgroundUrl: ''
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
                let currentCharacter = characters[id];
                if (this.classType === currentCharacter.classType) {
                    return currentCharacter;
                }
            }
        },
        updateBackgroundImage() {
            this.emblemBackgroundUrl = `https://bungie.net/${this.currentCharater().emblemBackground}`;
            // wait a bit for image to load
            setTimeout(() => {
                this.style = `background-image: url(${this.emblemBackgroundUrl});`;
            }, 200);
        }
    }
};