<template>
    <v-container class="grey lighten-5">
        <message-form :messages="messages" :messageAttr="message"/>
        <v-row>
            <v-col sm="9">
                <message-row v-for="message in sortedMessages" :key="message.id" :messages="messages" :message="message"
                             :editMessage="editMessage" :removeMessage="removeMessage"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>

    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import messagesApi from 'api/messages'


    export default {
        name: "MessagesList",
        components: {
            MessageRow, MessageForm
        },
        props: ['messages'],
        data() {
            return {
                message: null
            }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => -(a.id - b.id))
            }
        },
        methods: {
            editMessage(message) {
                this.message = message;
            },
            removeMessage(message) {
                messagesApi.remove(message.id).then(
                    result => {
                        if (result.ok) {
                            this.messages.splice(this.messages.indexOf(this.message), 1)
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>

</style>