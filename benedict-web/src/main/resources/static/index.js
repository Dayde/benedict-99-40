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
            default: Header,
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
