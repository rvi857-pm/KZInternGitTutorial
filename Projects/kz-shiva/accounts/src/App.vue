<template>
	<div id="app">
		<Header/>
		<Accounts
			:items="items"
			:fields="fields"
			:state="state"
			:updateItems="updateItems"
			:parse="parse"
		/>
	</div>
</template>

<script>
import Accounts from "./components/Accounts";
import Header from "./components/Header";

export default {
	name: "App",
	components: {
		Header,
		Accounts
	},
	data() {
		return {
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
				search = search.length
					? stack.pop() + " " + search
					: stack.pop();
			}
			this.state = { ...this.state, search: search };
			return true;
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
