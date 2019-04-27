Vue.component('item-category', {
    template: `
<div>
    <label>
        <input id="armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="ARMOR">
        <img class="form-img"
             src="https://bungie.net/common/destiny2_content/icons/ba288981fc651ad3dc23f3c211e8b209.jpg">
    </label>
    <label>
        <input id="helmet" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="HELMET">
        <img v-if="classType==='TITAN'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/898624eb5964b7456a03bfa70467fe2d.jpg">
        <img v-if="classType==='HUNTER'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/9ab0628c6ad2b56eac6aeda15aaaa02d.jpg">
        <img v-if="classType==='WARLOCK'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/567ba1358e11231ed78e1d91f0df4f47.jpg">
    </label>
    <label>
        <input id="gauntlets" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="GAUNTLETS">
        <img v-if="classType==='TITAN'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/5adad1c8a4bf69befae8f3798802204e.jpg">
        <img v-if="classType==='HUNTER'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/43632a486e585efb1497a051833dbe4f.jpg">
        <img v-if="classType==='WARLOCK'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/5963baf820fd850615e4a5e094290ee2.jpg">
    </label>
    <label>
        <input id="chest_armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="CHEST_ARMOR">
        <img v-if="classType==='TITAN'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/ab8935563ab99a73e4b7741d2563c266.jpg">
        <img v-if="classType==='HUNTER'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/762cc65c027e800eaad532b56dc434fc.jpg">
        <img v-if="classType==='WARLOCK'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/32680dbbb71ba9a5dddf40cd9bbe45cd.jpg">
    </label>
    <label>
        <input id="leg_armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="LEG_ARMOR">
        <img v-if="classType==='TITAN'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/878e0a9a1ac7b5f81937ff574c3979ff.jpg">
        <img v-if="classType==='HUNTER'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/2ecdee3a80f22e930a551025207da057.jpg">
        <img v-if="classType==='WARLOCK'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/b74d347dc23fa4c6a29086df8fe12c2d.jpg">
    </label>
    <label>
        <input id="class_armor" name="itemCategory" type="radio" v-model="mutableItemCategory"
               value="CLASS_ARMOR">
        <img v-if="classType==='TITAN'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/f6fa8c7171f6396f1dc6f8b14c17c927.jpg">
        <img v-if="classType==='HUNTER'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/b396e9b0df4508a7f33c0ab598834ab5.jpg">
        <img v-if="classType==='WARLOCK'" class="titan form-img"
             src="https://bungie.net/common/destiny2_content/icons/397aff565cb416cfb9f415d80e4f4d21.jpg">
    </label>
</div>
`,
    props: ['value', 'classType'],
    data() {
        return {
            mutableItemCategory: this.value
        }
    },
    watch: {
        mutableItemCategory(newVal) {
            this.$emit('input', newVal);
        }
    }
});