<template>

    <div>
        <i>{{message.id}}</i> - {{message.text}}
        <span>
        <input type="button" value="edit" @click="edit">
        <input type="button" value="remove" @click="remove">
        </span>
    </div>
</template>

<script>

    import {mapActions} from 'vuex'

    export default {
        name: "MessageRow",
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message)
            },
            remove() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style scoped>

</style>