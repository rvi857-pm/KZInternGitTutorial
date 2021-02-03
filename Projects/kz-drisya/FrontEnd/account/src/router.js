import Vue from 'vue'
import Router from 'vue-router'
import Buyer from './components/Buyer.vue';
import App from './components/Acounts.vue';
import Activity from './components/Activity.vue';
Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'account-router',
            component: App
        },
        {
            path: '/:id',
            name: 'buyer-router',
            props: true,
            component: Buyer
        },
        {
            path: '/activity',
            name: 'activity-router',
            props: true,
            component: Activity,
        }
    ]
})