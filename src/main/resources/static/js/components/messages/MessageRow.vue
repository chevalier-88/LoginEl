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
    export default {
        name: "MessageRow",
        props: ['messages', 'message', 'editMessage', 'removeMessage'],
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            remove() {
                this.removeMessage(this.message)
            }
        }
    }
</script>

<style scoped>

</style>