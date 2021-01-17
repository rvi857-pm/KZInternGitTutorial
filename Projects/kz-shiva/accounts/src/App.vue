<template>
	<div id="app">
		<Header :update="updatePage" />
		<div>
			<Home v-if="page == 0" />
			<Accounts
				v-else-if="page == 1"
				:items="items"
				:fields="fields"
				:state="state"
				:updateItems="updateItems"
			/>
			<Search
				v-else
				:state="state"
				:updatePage="updatePage"
				:parse="parse"
			/>
		</div>
	</div>
</template>

<script>
import Accounts from "./components/Accounts";
import Header from "./components/Header";
import Home from "./components/Home";
import Search from "./components/Search";

export default {
	name: "App",
	components: {
		Home,
		Header,
		Accounts,
		Search,
	},
	data() {
		return {
			page: 0,
			fields: [
				"id",
				"name",
				"ipDomain",
				"city",
				"state",
				"country",
				"type",
				"salesforceId",
			],
			items: [],
			state: {
				search: "",
				name: "",
				ipDomain: "",
				city: "",
				state: "",
				country: "",
				type: "",
				salesforceId: "",
				page: "",
				pageSize: "",
			},
		};
	},
	methods: {
		updatePage(value) {
			this.page = value;
		},

		updateItems(value) {
			this.items = value;
		},

		parse(value) {
			value += " ";
			let stack = [];
			let temp = "";
			let flag = false;
			let seperator = false;
			for (let c of value) {
				if (c == " ") {
					if (flag) temp += c;
					else {
						if (temp) {
							if (seperator) {
								let key = stack.pop();
								this.state = { ...this.state, [key]: temp };
								seperator = false;
							} else stack.push(temp);
							temp = "";
						}
					}
				} else if (c == '"') {
					flag = !flag;
					if (!flag) {
						if (seperator) {
							let key = stack.pop();
							this.state = { ...this.state, [key]: temp };
							seperator = false;
						} else stack.push(temp);
						temp = "";
					}
				} else if (c == ":") {
					seperator = true;
					if (temp) {
						stack.push(temp);
						temp = "";
					}
				} else temp += c;
			}
			let search = "";
			while (stack.length) {
				search = search.length ? stack.pop() + " " + search : stack.pop();
			}
			this.state = { ...this.state, "search": search };
			console.log(this.state)
		},
	},
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
	margin-top: 10px;
	margin-left: 5px;
	margin-right: 5px;
}
</style>
