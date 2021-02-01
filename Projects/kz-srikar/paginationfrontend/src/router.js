import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Account from './views/Account.vue'
import Buyer from './views/Buyer.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL ,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/:name',
            name: 'account',
            component: Account
        },
        {
            path: '/:name/:id',
            name: 'buyer',
            component: Buyer
        }
    ]
})