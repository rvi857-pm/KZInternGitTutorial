import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		home: {
			search: "",
			name: "",
			ipDomain: "",
			city: "",
			state: "",
			country: "",
			type: "",
			salesforceId: "",
			page: 1,
			pageSize: 10,
		},
		account: {
			page: 1,
			pageSize: 10,
		},
		buyer: {
			page: 1,
			pageSize: 10,
		},
	},
	getters: {
		getHome: (state) => {
			return state.home;
		},
		getAccount: (state) => {
			return state.account;
		},
		getBuyer: (state) => {
			return state.buyer;
		},
	},
	mutations: {
		setHome: (state, payload) => {
			state.home = { ...state.home, ...payload };
		},
		setAccount: (state, payload) => {
			state.account = { ...state.account, ...payload };
		},
		setBuyer: (state, payload) => {
			state.buyer = { ...state.buyer, ...payload };
		},
	},
});
