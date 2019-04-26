const routes = [
    {
        path: '/',
        components: {
            default: Home
        },
        props: {
            default: true
        }
    },
    {
        path: '/:username',
        components: {
            default: Platform
        },
        props: {
            default: true
        }
    },
    {
        path: '/:username/:platform',
        components: {
            default: ClassType
        },
        props: {
            default: true
        }
    },
    {
        path: '/:username/:platform/:classType/:itemCategory',
        components: {
            default: Form,
            result: Result
        },
        props: {
            default: true,
            result: false
        }
    }
];

const router = new VueRouter({
    mode: 'history',
    routes: routes
});

new Vue({router}).$mount('#app');
