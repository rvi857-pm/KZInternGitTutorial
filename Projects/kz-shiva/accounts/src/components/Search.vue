<template>
	<div id="form">
		<b-container class="bv-example-row bv-example-row-flex-cols">
			<b-row>
				<b-col cols="10">
					<b-form-input
						id="search"
						type="text"
						v-model="value"
						placeholder="Search"
					></b-form-input>
				</b-col>
				<b-col cols="1">
					<b-button
						id="submit"
						float
						size="sm"
						variant="primary"
						@click="onSubmit"
						>Submit</b-button
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
		parse: Function,
		update: Function
	},

	data() {
		return {
			value: "",
			show: true,
		};
	},
	methods: {
		onSubmit() {
			let myPromise = new Promise((resolve, reject) => {
				let ret = this.parse(this.value)
				if(ret) resolve();
				else reject();
			})
			myPromise.then(()=>{
				this.update();
			})
			.catch()
		},
	},
};
</script>

<style>
#form {
	margin: 20px;
}
</style>
