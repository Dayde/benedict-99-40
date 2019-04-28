const routes = [
    {
        path: '/',
        components: {
            default: Home
        }
    },
    {
        path: '/sweep/:userId/:platform',
        components: {
            default: ClassType
        }
    },
    {
        path: '/sweep/:userId/:platform/:classType/:itemCategory',
        components: {
            default: Header,
            result: Result
        }
    },
    {
        path: '/settings',
        name: 'settings',
        components: {
            default: Settings
        }
    },
    {
        path: '*',
        redirect: '/'
    }
];

const router = new VueRouter({
    mode: 'history',
    routes: routes
});

new Vue({router}).$mount('#app');
