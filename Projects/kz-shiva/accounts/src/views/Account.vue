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
import { mapGetters } from "vuex";

export default {
	name: "account",

	components: {
		Table,
	},

	data() {
		return {
			fields: [
				"id",
				"city",
				"state",
				"country",
				"source",
				"job_level",
				"job_function",
			],
			items: [],
		};
	},

	computed: {
		...mapGetters({
			state: "getAccount",
		}),
	},

	methods: {
		updateItems(items) {
			this.items = items;
		},

		updateState(updatedState) {
			this.$store.commit('setAccount', updatedState);
			return true;
		},

		rowClicked(item) {
			this.$store.commit('setBuyer', item);
			this.$router.push(`/buyer/${item.id}`);
		},

		getBuyerUrl() {
			return api.getBuyerUrl(this.state);
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
