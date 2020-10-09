<template>
    <div>
        <v-text-field label="New message" placeholder="enter your message" v-model="text"/>
        <v-btn @click="save">
            Save
        </v-btn>
    </div>
</template>

<script>

    import {mapActions} from 'vuex'

    export default {
        name: "MessageForm",
        props: ['messageAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function (newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {

                const message = {id: this.id, text: this.text}

                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style scoped>

</style>