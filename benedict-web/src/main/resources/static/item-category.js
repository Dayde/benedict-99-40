let armors = {
    HELMET: {
        TITAN: 'https://bungie.net/common/destiny2_content/icons/898624eb5964b7456a03bfa70467fe2d.jpg',
        HUNTER: 'https://bungie.net/common/destiny2_content/icons/9ab0628c6ad2b56eac6aeda15aaaa02d.jpg',
        WARLOCK: 'https://bungie.net/common/destiny2_content/icons/567ba1358e11231ed78e1d91f0df4f47.jpg'
    },
    GAUNTLETS: {
        TITAN: 'https://bungie.net/common/destiny2_content/icons/5adad1c8a4bf69befae8f3798802204e.jpg',
        HUNTER: 'https://bungie.net/common/destiny2_content/icons/43632a486e585efb1497a051833dbe4f.jpg',
        WARLOCK: 'https://bungie.net/common/destiny2_content/icons/5963baf820fd850615e4a5e094290ee2.jpg'
    },
    CHEST_ARMOR: {
        TITAN: 'https://bungie.net/common/destiny2_content/icons/ab8935563ab99a73e4b7741d2563c266.jpg',
        HUNTER: 'https://bungie.net/common/destiny2_content/icons/762cc65c027e800eaad532b56dc434fc.jpg',
        WARLOCK: 'https://bungie.net/common/destiny2_content/icons/32680dbbb71ba9a5dddf40cd9bbe45cd.jpg'
    },
    LEG_ARMOR: {
        TITAN: 'https://bungie.net/common/destiny2_content/icons/878e0a9a1ac7b5f81937ff574c3979ff.jpg',
        HUNTER: 'https://bungie.net/common/destiny2_content/icons/2ecdee3a80f22e930a551025207da057.jpg',
        WARLOCK: 'https://bungie.net/common/destiny2_content/icons/b74d347dc23fa4c6a29086df8fe12c2d.jpg'
    },
    CLASS_ARMOR: {
        TITAN: 'https://bungie.net/common/destiny2_content/icons/f6fa8c7171f6396f1dc6f8b14c17c927.jpg',
        HUNTER: 'https://bungie.net/common/destiny2_content/icons/b396e9b0df4508a7f33c0ab598834ab5.jpg',
        WARLOCK: 'https://bungie.net/common/destiny2_content/icons/397aff565cb416cfb9f415d80e4f4d21.jpg'
    }
};

Vue.component('armor', {
    template: '<img :src="imageUrl">',
    props: {
        armor: String,
        classType: {
            default: localStorage.classType
        }
    },
    computed: {
        imageUrl() {
            return armors[this.armor][this.classType];
        }
    }
});


Vue.component('item-category', {
    template: `
<div>
    <label>
        <input id="armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="ARMOR">
        <img class="form-img"
             src="https://bungie.net/common/destiny2_content/icons/ba288981fc651ad3dc23f3c211e8b209.jpg">
    </label>
    <label v-for="(value, armor) in armors">
        <input :id="armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               :value="armor">
           <armor :classType="classType" :armor="armor" class="form-img"></armor>
    </label>
</div>
`,
    props: ['value', 'classType'],
    data() {
        return {
            mutableItemCategory: this.value,
            armors: armors
        }
    },
    watch: {
        mutableItemCategory(newVal) {
            this.$emit('input', newVal);
        }
    }
});