<template>
	<div id="app">
		<Header />
		<Accounts
			:items="items"
			:fields="fields"
			:state="state"
			:updateItems="updateItems"
			:parse="parse"
			:updateState="updateState"
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

		/**
		 * This method updates the items in the table as required.
		 * @param items updated list of items to render on the table
		 */
		updateItems( items ) {
			this.items = items;
		},

		/**
		 * This method updates the state object of the data members
		 * @param updatedState
		 * @return boolean to mark as successful
		 */
		updateState( updatedState ) {
			this.state = updatedState;
			return true;
		},

		/**
		 * This method is used to parse the value in the search box. It uses stack data structure
		 * to achieve required functionality.
		 * @param searchValue string that needs to be parsed
		 * @return the updated key value pairs
		 */
		parse( searchValue ) {
			searchValue += " ";
			let stack = [];
			let temp = "";
			let flag = false;
			let seperator = false;
			let returnValue = {};
			for (let c of searchValue) {
				if (c == " ") {
					if (flag) temp += c;
					else {
						if (temp) {
							if (seperator) {
								let key = stack.pop();
								returnValue = { ...returnValue, [key]: temp };
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
							returnValue = { ...returnValue, [key]: temp };
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
			returnValue = { ...returnValue, search: search };
			return returnValue;
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
