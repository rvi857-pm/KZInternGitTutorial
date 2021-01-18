<template>
	<div id="form">
		<b-container class="bv-example-row bv-example-row-flex-cols">
			<b-row>
				<b-col>
					<b-form inline>
						<label class="sr-only" for="inline-form-input-search"
							>Search</label
						>
						<b-form-input
							id="inline-form-input-search"
							class="w-50 mb-2 mr-sm-2 mb-sm-0"
							type="text"
							v-model="value"
							placeholder="Search"
						></b-form-input>

						<label class="sr-only" for="inline-form-input-pagesize"
							>Page Size</label
						>
						<b-form-input
							id="inline-form-input-pagesize"
							class="mb-2 mr-sm-2 mb-sm-0"
							type="number"
							v-model="size"
							placeholder="Page Size"
						></b-form-input>

						<b-button
							id="submit"
							float
							size="sm"
							variant="primary"
							@click="onSubmit"
							>Submit</b-button
						>
					</b-form>
				</b-col>
			</b-row>
		</b-container>
	</div>
</template>

<script>
export default {
	props: {
		state: Object,
		parse: Function,
		updateState: Function,
		search: Function,
		updateTable: Function,
	},

	data() {
		return {
			value: "",
			size: "",
			show: true,
		};
	},
	methods: {
		/**
		 * This method updates the state and then calls appropriate methods
		 * to update the UI.
		 * @param
		 * @return
		 */
		onSubmit() {
			let myPromise = new Promise((resolve, reject) => {
				this.updateTable(false);
				let state = this.parse(this.value);

				state = this.size
					? { ...state, page: "0", pageSize: this.size }
					: { ...state, page: "", pageSize: "" };

				if (this.updateState(state)) resolve();
				else reject();
			});

			myPromise
				.then(() => {
					this.search();
				})
				.catch();
		},
	},
};
</script>

<style>
#form {
	margin: 20px;
}
</style>
