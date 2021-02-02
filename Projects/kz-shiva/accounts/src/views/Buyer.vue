<template>
	<div id="buyer">
		<buyer-info />
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
import BuyerInfo from "../components/BuyerInfo.vue";

export default {
	name: "buyer",

	components: {
		Table,
		BuyerInfo,
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
            account: "getAccount",
		}),
	},

	methods: {
		updateItems(items) {
			this.items = items;
		},

		updateState(updatedState) {
			this.$store.commit("setAccount", updatedState);
			return true;
		},

		rowClicked() {},

		getActivityUrl() {
			return api.getActivityUrl(this.state);
		},
	},
	mounted() {
		let items = [
			{
				text: "Accounts",
				to: "/",
			},
			{
				text: this.account.name,
				to: `/account/${this.account.account_id}`,
            },
            {
				text: this.state.id,
				to: `/buyer/${this.state.id}`,
            },
		];
		this.$store.commit("setItems", items);
	},
};
</script>

<style>
#buyer {
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
