<template>
	<div id="form">
		<b-container>
			<b-row>
				<b-col offset="3">
					<b-form @submit="onSubmit" @reset="onReset" v-if="show">
						<b-form-group
							id="input-group-1"
							label="AccountName:"
							label-for="input-1"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-1"
								v-model="state.id"
								placeholder="Enter account name"
							>
							</b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-2"
							label="Domain:"
							label-for="input-2"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-2"
								v-model="state.ipDomain"
								placeholder="Enter domain"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-3"
							label="City:"
							label-for="input-3"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-3"
								v-model="state.city"
								placeholder="Enter city"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-4"
							label="State:"
							label-for="input-4"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-4"
								v-model="state.state"
								placeholder="Enter state"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-5"
							label="Country:"
							label-for="input-5"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-5"
								v-model="state.country"
								placeholder="Enter country"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-6"
							label="Type:"
							label-for="input-6"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-6"
								v-model="state.type"
								placeholder="Enter type"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-7"
							label="Accound Id:"
							label-for="input-7"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-7"
								v-model="state.salesforceId"
								placeholder="Enter accountId"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-8"
							label="Page:"
							label-for="input-8"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-8"
								v-model="state.page"
								type="number"
								placeholder="Enter page number"
							></b-form-input>
						</b-form-group>

						<b-form-group
							id="input-group-9"
							label="Page Size:"
							label-for="input-9"
							label-cols-lg="2"
							content-cols-lg="4"
							label-size="md"
						>
							<b-form-input
								id="input-9"
								v-model="state.pageSize"
								type="number"
								placeholder="Enter page size"
							></b-form-input>
						</b-form-group>
					</b-form>
				</b-col>
			</b-row>
			<b-row >
				<b-col>
					<b-button
						id="submit"
						float
						type="submit"
						variant="primary"
						>Submit</b-button
					>
					<b-button id="reset" type="reset" variant="danger"
						>Reset</b-button
					>
				</b-col>
			</b-row>
		</b-container>
	</div>
</template>

<script>
export default {
	props: {
		state: Object,
		updatePage: Function,
	},

	data() {
		return {
			show: true,
		};
	},
	methods: {
		onSubmit(event) {
			event.preventDefault();
			this.updatePage(1);
		},
		onReset(event) {
			event.preventDefault();
			// Reset our form values
			this.state.name = "";
			this.state.ipDomain = "";
			this.state.city = "";
			this.state.state = "";
			this.state.country = "";
			this.state.type = "";
			this.state.salesforceId = "";
			this.state.page = "";
			this.state.pageSize = "";
			// Trick to reset/clear native browser form validation state
			this.show = false;
			this.$nextTick(() => {
				this.show = true;
			});
		},
	},
};
</script>

<style>
#form {
	margin: 20px;
}
#submit{
	margin: 4px;
}
</style>
