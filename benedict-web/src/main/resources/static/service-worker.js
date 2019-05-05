const PRECACHE = 'precache-v1';

const PRECACHE_URLS = [];

self.addEventListener('install', event => {
    event.waitUntil(
        caches.open(PRECACHE)
            .then(cache => cache.addAll(PRECACHE_URLS))
            .then(self.skipWaiting())
    );
});

self.addEventListener('activate', event => {
    const currentCaches = [PRECACHE];
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return cacheNames.filter(cacheName => !currentCaches.includes(cacheName));
        }).then(cachesToDelete => {
            return Promise.all(cachesToDelete.map(cacheToDelete => {
                return caches.delete(cacheToDelete);
            }));
        }).then(() => self.clients.claim())
    );
});

self.addEventListener('fetch', event => {
    // Skip cross-origin requests, like those for Google Analytics.
    if (event.request.url.startsWith(self.location.origin)) {
        event.respondWith(fetch(event.request));
    }
});
