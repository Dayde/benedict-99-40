const routes = [
    {
        path: '/:username/:platform/:classType/:itemCategory',
        components: {
            form: Form,
            result: Result
        },
        props: {
            form: true,
            result: false
        }
    }
];

const router = new VueRouter({
    mode: 'history',
    routes: routes
});

new Vue({router}).$mount('#app');
