// For mobile, prevents context menu event propagation
// on touchHold to allow tooltips to show
window.oncontextmenu = function (event) {
    event.preventDefault();
    event.stopPropagation();
    return false;
};

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
