import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'home',
		component: () => import('@/views/Home'),
	},
	{
		path: '/account/:id',
		name: 'account',
		component: () => import('@/views/Account')
	},
	{
		path: '/buyer/:id',
		name: 'buyer',
		component: () => import('@/views/Buyer')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router