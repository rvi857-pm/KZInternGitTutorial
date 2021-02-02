<template>
	<div v-if="showTable">
		<b-pagination
			id="page"
			v-if="localPageNum"
			v-model="localPageNum"
			:total-rows="totalLength"
			:per-page="perPage"
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
	},

	data() {
		return {
			PageNum: 1,
			totalLength: 0,
			perPage: 10,
			showTable: false,
		};
	},

	watch: {
		state: function(){
			this.update();
		},

		PageNum: function() {
			if (this.localPageNum) {
				this.updateShowTable(false);

				axios
					.get(url)
					.then((res) => {
						this.updateItems(res.data.content);
						this.updateShowTable(true);
					})
					.catch((err) => {
						console.log(err);
					});
			}
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
		 * @param size elements per a single page
		 */
		updatePerPage(size) {
			this.perPage = size;
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
			let url = "http://localhost:8080/accounts?metrics=all&page=0&page_size=10";
			axios
				.get(url)
				.then((res) => {
					this.updatePageNum(this.state.page + 1);
					this.updatePerPage(this.state.pageSize);
					this.updateTotalLength(response.data.totalElements);
					this.updateItems(res.data.data);
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
