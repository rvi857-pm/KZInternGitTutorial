<template>
	<div id="app">
		<Table
			:items="items"
			:fields="fields"
			:state="state"
			:updateItems="updateItems"
			:updateState="updateState"
			:rowClicked="rowClicked"
			:getUrl="getBuyerUrl"
		/>
	</div>
</template>

<script>
import Table from "@/components/Table";
import api from "../util/ApiUrl";

export default {
	name: "account",

	components: {
		Table,
	},

	data() {
		return {
			fields: [
				"city",
				"state",
				"country",
				"source",
				"job_level",
				"job_function",
			],
			items: [],
			state: {
				account_id: null,
				page: 1,
				pageSize: 10,
			},
		};
	},

	methods: {
		updateItems(items) {
			this.items = items;
		},

		updateState(updatedState) {
			this.state = updatedState;
			return true;
		},

		rowClicked(item, index) {
			console.log(item, index);
		},

		setAccount_id(value) {
			this.state.account_id = value;
		},

		getBuyerUrl(){
			return api.getBuyerUrl(this.state);
		}
	},

	created() {
		this.setAccount_id(this.$route.params.id);
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
