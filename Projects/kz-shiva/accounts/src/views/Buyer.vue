<template>
	<div id="app">
		<Table
			:items="items"
			:fields="fields"
			:state="state"
			:updateItems="updateItems"
			:updateState="updateState"
			:rowClicked="rowClicked"
			:getUrl="getActivityUrl"
		/>
	</div>
</template>

<script>
import Table from "@/components/Table";
import api from "../util/ApiUrl";
import { mapGetters } from "vuex";

export default {
	name: "buyer",

	components: {
		Table,
	},

	data() {
		return {
			fields: ["id", "datetime", "activity_type", "details"],
			items: [],
		};
	},

	computed: {
		...mapGetters({
			state: "getBuyer",
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

		rowClicked(item, index) {
			console.log(item, index);
		},

		setBuyer_id(value) {
			this.state.buyer_id = value;
		},

		getActivityUrl() {
			return api.getActivityUrl(this.state);
		},
	},

	created() {
		this.setBuyer_id(this.$route.params.id);
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
