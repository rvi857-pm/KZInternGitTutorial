import Vue from 'vue'
import Router from 'vue-router'
import Single from './views/single.vue'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/single/:id',
      name: 'single',
      component: Single
    },
    {
        path: '/',
        name: 'home',
        component: Home
      }
  ]
})