import Vue from 'vue'
import Router from 'vue-router'
import Single from './views/account.vue'
import Home from './views/Home.vue'
import Buyer from './views/buyer.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/account/:id',
      name: 'account',
      component: Single
    },
    {
      path: '/buyer/:id',
      name: 'buyer',
      component: Buyer
    },
    {
        path: '/',
        name: 'home',
        component: Home
      }
  ]
})