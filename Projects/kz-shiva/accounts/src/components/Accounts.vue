<template>
	<div>
		<Search
			:state="state"
			:parse="parse"
			:search="updateComponent"
			:updateState="updateState"
			:updateTable="updateShowTable"
		/>
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
			<b-table id="my-table" :items="items" :fields="fields"></b-table>
		</div>
	</div>
</template>

<script>
import axios from "axios";
import Search from "./Search";

export default {
	components: {
		Search,
	},

	props: {
		fields: Array,
		items: Array,
		state: Object,
		updateItems: Function,
		parse: Function,
		updateState: Function,
	},

	data() {
		return {
			localPageNum: 0,
			totalLength: 0,
			perPage: 0,
			showTable: false,
		};
	},

	watch: {
		localPageNum: function() {
			if (this.localPageNum) {
				this.updateShowTable(false);
				let queryUrl = this.getUrl();
				let url = "http://localhost:8080/accounts";
				url += queryUrl ? "?" + queryUrl : "";

				axios
					.get(url)
					.then((res) => {
						this.updateItems(res.data.content);
					})
					.catch((err) => {
						console.log(err);
					});
				this.updateShowTable(true);
			}
		},
	},

	methods: {
		updateShowTable(value) {
			this.showTable = value;
		},

		updateTotalLength(length) {
			this.totalLength = length;
		},

		updatePerPage(size) {
			this.perPage = size;
		},

		updateLocalPageNum(value) {
			this.localPageNum = value;
		},

		urlUtility(param1, param2) {
			return param1 ? "&" + param2 : param2;
		},

		getUrl() {
			let url = "";

			url += this.state.search
				? this.urlUtility(url, "search=") + this.state.search
				: "";

			url += this.state.name
				? this.urlUtility(url, "name=") + this.state.name
				: "";

			url += this.state.ipDomain
				? this.urlUtility(url, "ipDomain=") + this.state.ipDomain
				: "";

			url += this.state.city
				? this.urlUtility(url, "city=") + this.state.city
				: "";

			url += this.state.state
				? this.urlUtility(url, "state=") + this.state.state
				: "";

			url += this.state.country
				? this.urlUtility(url, "country=") + this.state.country
				: "";

			url += this.state.type
				? this.urlUtility(url, "type=") + this.state.type
				: "";

			url += this.state.salesforceId
				? this.urlUtility(url, "salesforceId=") + this.state.salesforceId
				: "";

			url += this.localPageNum
				? this.urlUtility(url, "page=") + (this.localPageNum - 1)
				: "";

			url += this.localPageNum
				? this.urlUtility(url, "page_size=") + this.state.pageSize
				: "";

			return url;
		},

		updateComponent() {
			if (this.state.page)
				this.updateLocalPageNum(Number(this.state.page) + 1);
			else this.updateLocalPageNum(0);

			let queryUrl = this.getUrl();
			let url = "http://localhost:8080/accounts";
			url += queryUrl ? "?" + queryUrl : "";

			axios
				.get(url)
				.then((res) => {
					if (this.localPageNum) {
						this.updateItems(res.data.content);
						this.updatePerPage(Number(this.state.pageSize));
						this.updateTotalLength(res.data.totalElements);
					} else {
						this.updateItems(res.data);
					}
					this.updateShowTable(true);
				})
				.catch((err) => {
					console.log(err);
				});
		},
	},
};
</script>

<style>
#page {
	margin-top: 20px;
}
</style>
