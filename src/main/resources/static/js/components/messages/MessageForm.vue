<template>
    <div>
        <v-text-field label="New message" placeholder="enter your message" v-model="text"/>
        <v-btn @click="save">
            Save
        </v-btn>
    </div>
</template>

<script>

    import messageApi from 'api/messages'

    export default {
        name: "MessageForm",
        props: ['messages', 'messageAttr'],
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
            save() {

                const message = {id: this.id, text: this.text}

                if (this.id) {
                    messageApi.update(message).then(
                        result => result.json().then(
                            data => {
                                const index = this.messages.findIndex(item => item.id === data.id)
                                this.messages.splice(index, 1, data)
                            }
                        ))
                } else {
                    messageApi.add(message).then(result => result.json().then(
                        data => {

                            const index = this.messages.findIndex(item => item.id === data.id)
                            if(index > -1){
                                this.messages.splice(index, 1, data)
                            }else{
                                this.messages.push(data)
                            }

                        }
                    ))
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style scoped>

</style>