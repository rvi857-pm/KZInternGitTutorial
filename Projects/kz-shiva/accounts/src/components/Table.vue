<template>
	<div v-if="showTable">
		<b-pagination
			id="page"
			v-if="pageNum"
			v-model="pageNum"
			:total-rows="totalLength"
			:per-page="state.pageSize"
			aria-controls="my-table"
			align="center"
		></b-pagination>
		<b-table
			id="my-table"
			:items="items"
			:fields="fields"
			@row-clicked="rowClicked"
		></b-table>
	</div>
</template>

<script>
import axios from "axios";

export default {
	name: "table",
	props: {
		fields: Array,
		items: Array,
		state: Object,
		updateItems: Function,
		updateState: Function,
		getUrl: Function,
		rowClicked: Function,
		tableId: String,
	},

	data() {
		return {
			pageNum: 1,
			totalLength: 0,
			showTable: false,
		};
	},

	watch: {
		state: function(){
			this.update();
		},

		pageNum: function() {
			this.updateState({...this.state, page: this.pageNum});
		},
	},

	methods: {
		/**
		 * @param value flag variable to show the table
		 */
		updateShowTable(value) {
			this.showTable = value;
		},

		/**
		 * @param length total elements without the page filter
		 */
		updateTotalLength(length) {
			this.totalLength = length;
		},

		/**
		 * @param value current index of the page
		 */
		updatePageNum(value) {
			this.PageNum = value;
		},

		/**
		 * This method updates component by calling corresponding api.
		 * @param
		 * @return
		 */
		update() {
			this.updateShowTable(false);
			let url = this.getUrl();
			axios
				.get(url)
				.then((response) => {
					this.updatePageNum(this.state.page);
					this.updateTotalLength(response.data.totalElements);
					this.updateItems(response.data.data);
					this.updateShowTable(true);
				})
				.catch((err) => {
					console.log(err);
				});
		},
	},
	mounted(){
		this.update();
	}
};
</script>

<style>
#page {
	margin-top: 20px;
}
</style>
