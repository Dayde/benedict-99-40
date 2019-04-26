const Form = {
    template: `
    <div class="form-container">
        <h2 class="title">Benedict 99-40 helps you sweep your vault</h2>
        <form action="items" method="get" target="result">
            <div>
                <input id="username" name="username" placeholder="Username" required
                       type="text" v-model="username">
            </div>

            <div>
                <label>
                    <input name="platform" type="radio" v-model="platform" value="1">
                    <i class="fab fa-xbox platform-icon"></i>
                </label>
                <label>
                    <input name="platform" type="radio" v-model="platform" value="2">
                    <i class="fab fa-playstation  platform-icon"></i>
                </label>
                <label>
                    <input name="platform" type="radio" v-model="platform" value="4">
                    <i class="fab fa-battle-net platform-icon"></i>
                </label>
            </div>

            <div>
                <label>
                    <input id="titan" name="classType" type="radio" v-model="classType" value="TITAN">
                    <img class="form-img"
                         src="https://bungie.net/common/destiny2_content/icons/8956751663b4394cd41076f93d2dd0d6.png">
                </label>
                <label>
                    <input id="hunter" name="classType" type="radio" v-model="classType" value="HUNTER">
                    <img class="form-img"
                         src="https://bungie.net/common/destiny2_content/icons/e7324e8c29c5314b8bce166ff167859d.png">
                </label>
                <label>
                    <input id="warlock" name="classType" type="radio" v-model="classType" value="WARLOCK">
                    <img class="form-img"
                         src="https://bungie.net/common/destiny2_content/icons/bf7b2848d2f5fbebbf350d418b8ec148.png">
                </label>
            </div>

            <div class="gear-type">
                <label>
                    <input id="armor" name="itemCategory" type="radio" v-model="itemCategory"
                           value="ARMOR">
                    <img class="form-img"
                         src="https://bungie.net/common/destiny2_content/icons/ba288981fc651ad3dc23f3c211e8b209.jpg">
                </label>
                <label>
                    <input id="helmet" name="itemCategory" type="radio" v-model="itemCategory"
                           value="HELMET">
                    <img class="titan form-img"
                         src="https://bungie.net/common/destiny2_content/icons/898624eb5964b7456a03bfa70467fe2d.jpg">
                </label>
                <label>
                    <input id="gauntlets" name="itemCategory" type="radio" v-model="itemCategory"
                           value="GAUNTLETS">
                    <img class="titan form-img"
                         src="https://bungie.net/common/destiny2_content/icons/5adad1c8a4bf69befae8f3798802204e.jpg">
                </label>
                <label>
                    <input id="chest_armor" name="itemCategory" type="radio" v-model="itemCategory"
                           value="CHEST_ARMOR">
                    <img class="titan form-img"
                         src="https://bungie.net/common/destiny2_content/icons/ab8935563ab99a73e4b7741d2563c266.jpg">
                </label>
                <label>
                    <input id="leg_armor" name="itemCategory" type="radio" v-model="itemCategory"
                           value="LEG_ARMOR">
                    <img class="titan form-img"
                         src="https://bungie.net/common/destiny2_content/icons/878e0a9a1ac7b5f81937ff574c3979ff.jpg">
                </label>
                <label>
                    <input id="class_armor" name="itemCategory" type="radio" v-model="itemCategory"
                           value="CLASS_ARMOR">
                    <img class="titan form-img"
                         src="https://bungie.net/common/destiny2_content/icons/f6fa8c7171f6396f1dc6f8b14c17c927.jpg">
                </label>
            </div>
        </form>
    </div>
`,
    props: [
        'platform',
        'username',
        'classType',
        'itemCategory'
    ],
    watch: {
        platform(newVal) {
            this.$router.push({params: {...this.$route.params, platform: newVal}});
        },
        username(newVal) {
            this.$router.push({params: {...this.$route.params, username: newVal}});
        },
        classType(newVal) {
            this.$router.push({params: {...this.$route.params, classType: newVal}});
        },
        itemCategory(newVal) {
            this.$router.push({params: {...this.$route.params, itemCategory: newVal}});
        }
    }
};