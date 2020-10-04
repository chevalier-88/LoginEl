<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>LoginEl</v-toolbar-title>
            <v-spacer/>
            <div v-if="profile">
                <span>{{profile.name}}&nbsp;</span>
                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </div>
        </v-app-bar>

        <v-main>

            <v-container v-if="!profile" fluid>
                <div>Your nead authorization by <a href="/login">Google</a></div>
            </v-container>
            <v-container v-if="profile" fluid>
                <div>
                    <messages-list :messages="messages"/>
                </div>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessagesList.vue'
    import {addHandler} from "util/ws";


    export default {
        name: "App",
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {

            addHandler(data => {
                if (data.objectType === "MESSAGE") {
                    const index = this.messages.findIndex(item => item.id === data.body.id)
                    switch (data.eventType) {
                        case'CREATE':
                        case'UPDATE':
                            if (index > -1) {
                                this.messages.splice(index, 1, data.body)
                            } else {
                                this.messages.push(data.body)
                            }
                            break;
                        case'REMOVE':
                            this.messages.splice(index, 1)
                            break;
                        default:
                            console.error('Looks like the event type is unknown... "${data.eventType}"')
                    }
                } else {
                    console.error('Looks like the object type is unknown..."${data.objectType}"')
                }
            })
        }
    }
</script>

<style scoped>

</style>