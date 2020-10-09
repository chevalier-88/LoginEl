<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>LoginEl</v-toolbar-title>
            <v-spacer/>
            <div v-if="profile">
                <v-btn v-if="profile" :disabled="$route.path === '/'" @click="showMessages">
                    MessagesList
                </v-btn>
                <v-btn v-if="profile">{{profile.name}}&nbsp;</v-btn>
                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </div>
        </v-app-bar>

        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import {addHandler} from "util/ws";


    export default {
        name: "App",
        computed: {
            ...mapState([
                'profile'
            ])
        },
        methods: {
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
            showMessages() {
                this.$router.push("/")
            }
        },
        created() {

            addHandler(data => {
                if (data.objectType === "MESSAGE") {
                    switch (data.eventType) {
                        case'CREATE':
                            this.addMessageMutation(data.body)
                        case'UPDATE':
                            this.updateMessageMutation(data.body)
                            break;
                        case'REMOVE':
                            this.removeMessageMutation(data.body)
                            break;
                        default:
                            console.error('Looks like the event type is unknown... "${data.eventType}"')
                    }
                } else {
                    console.error('Looks like the object type is unknown..."${data.objectType}"')
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace("/auth")
            }
        }
    }
</script>

<style scoped>

</style>