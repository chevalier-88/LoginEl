function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}


var messageApi = Vue.resource("/message{/id}")


Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="enter your message" v-model="text">' +
        '<input type="button" value="Enter" @click="save">' +
        '</div>',
    watch: {
        messageAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    methods: {
        save: function () {
            var message = {text: this.text}

            if (this.id) {
                messageApi.update({id: this.id}, message).then(
                    result => result.json().then(
                        data => {
                            var index = getIndex(this.messages, data.id)
                            this.messages.splice(index, 1, data)
                            this.text = ''
                            this.id = ''
                        }
                    ))
            } else {
                messageApi.save({}, message).then(result => result.json().then(
                    data => {
                        this.messages.push(data)
                        this.text = ''
                        this.id = ''
                    }
                ))
            }
        }
    }
})

Vue.component('message-row', {
    props: ['messages', 'message', 'editMethod'],
    template: '<div>' +
        '<i>{{message.id}}</i> - {{message.text}}' +
        '<span>' +
        '<input type="button" value="edit" @click="editMessage">' +
        '<input type="button" value="remove" @click="removeMessage">' +
        '</span>' +
        '</div>',
    methods: {
        editMessage: function () {
            this.editMethod(this.message)
        },
        removeMessage: function () {
            messageApi.remove({id: this.message.id}).then(
                result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(this.message), 1)
                    }
                }
            )
        }
    }
})

Vue.component('messages-list', {
    props: ['messages'],
    data: function () {
        return {
            message: null
        }
    },
    template:
        '<div>' +
        '<message-form :messages="messages" :messageAttr="message"/>' +
        '<message-row v-for="message in messages" :key="message.id" :messages="messages" :message="message" :editMethod="editMethod"/>' +
        '</div>',
    created: function () {
        messageApi.get().then(
            result => result.json().then(
                data => data.forEach(
                    message => this.messages.push(message)
                )
            )
        )
    },
    methods: {
        editMethod: function (message) {
            this.message = message;
        }
    }
})


var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: []
    }
})