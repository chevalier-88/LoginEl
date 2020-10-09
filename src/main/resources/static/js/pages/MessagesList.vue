<template>
    <v-container class="grey lighten-5">
        <message-form :messageAttr="message"/>
        <v-row>
            <v-col sm="9">
                <message-row v-for="message in this.sortedMessages" :key="message.id" :message="message"
                             :editMessage="editMessage"/>

            </v-col>
        </v-row>
    </v-container>
</template>

<script>

    import { mapGetters } from 'vuex'
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'


    export default {
        name: "MessagesList",
        components: {
            MessageRow, MessageForm
        },
        data() {
            return {
                message: null
            }
        },
        computed:{ ...mapGetters(['sortedMessages'])},
        methods: {
            editMessage(message) {
                this.message = message;
            }
        }
    }
</script>

<style scoped>

</style>