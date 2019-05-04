Vue.use(VueTippy, {
    theme: 'dark'
});

const routes = [
    {
        path: '/',
        component: Home
    },
    {
        path: '/sweep/:userId/:platform',
        component: ClassType
    },
    {
        path: '/sweep/:userId/:platform/:classType/:itemCategory',
        component: Sweep
    },
    {
        path: '/settings',
        name: 'settings',
        component: Settings
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
