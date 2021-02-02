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
				<b-col cols="2">
					<b-button v-b-modal.upload-file variant="primary"
						>Add Accounts</b-button
					>

					<b-modal
						id="upload-file"
						centered
						title="upload the file"
						ok-title="Submit"
						@ok="onOk"
					>
						<b-form-file
							id="file-input"
							v-model="file"
							type="multipart/form-data"
						></b-form-file>
					</b-modal>
				</b-col>
			</b-row>
		</b-container>
	</div>
</template>

<script>
import axios from "axios";

export default {
	name: "search",

	props: {
		state: Object,
		parse: Function,
		updateState: Function,
	},

	data() {
		return {
			value: "",
			size: "",
			show: true,
			file: null,
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
			let state = this.parse(this.value);

			state = this.size
				? { ...state, page: 1, pageSize: this.size }
				: { ...state, page: 1, pageSize: 10 };

			this.updateState(state);
		},
		onOk() {
			this.fileUpload(this.file)
				.then((res) => {
					console.log(res);
				})
				.catch((err) => {
					console.log(err);
				});
		},
		fileUpload(file) {
			const url = "http://localhost:8080/upload-accounts";
			const formData = new FormData();
			formData.append("file", file);
			const config = {
				headers: {
					"content-type": "multipart/form-data",
				},
			};
			return axios.post(url, formData, config);
		},
	},
};
</script>

<style>
#form {
	margin: 20px;
}
</style>
