if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/service-worker.js');
}

const isMobile = {
    Android: function () {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function () {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function () {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function () {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function () {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function () {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};

// For mobile, prevents context menu event propagation
// on touchHold to allow tooltips to show
if (isMobile.any()) {
    window.oncontextmenu = function (event) {
        event.preventDefault();
        event.stopPropagation();
        return false;
    };
}

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
