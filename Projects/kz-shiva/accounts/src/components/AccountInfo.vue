<template>
	<div id="head">
		<div id="info">
			<b-list-group>
				<b-list-group-item>name : {{ state.name }}</b-list-group-item>
				<b-list-group-item
					>ip_domain : {{ state.ip_domain }}</b-list-group-item
				>
				<b-list-group-item>city : {{ state.city }}</b-list-group-item>
				<b-list-group-item>state : {{ state.state }}</b-list-group-item>
				<b-list-group-item
					>country : {{ state.country }}</b-list-group-item
				>
				<b-list-group-item>type : {{ state.type }}</b-list-group-item>
				<b-list-group-item
					>salesforce_id :
					{{ state.salesforce_id }}</b-list-group-item
				>
				<b-list-group-item>score : {{ state.score }}</b-list-group-item>
				<b-list-group-item
					>marketing_qualified :
					{{ state.marketing_qualified }}</b-list-group-item
				>
			</b-list-group>
		</div>
		<div id="charts">
			<div>
				<h3 style="text-align:center;">Location Count</h3>
				<pie-chart :args="locationArgs" />
			</div>
			<div>
				<h3 style="text-align:center;">Persona Count</h3>
				<pie-chart :args="personaArgs" />
			</div>
			<div>
				<h3 style="text-align:center;">Activity Count</h3>
				<pie-chart :args="activityArgs" />
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
import PieChart from "./PieChart.vue";
export default {
	name: "account-info",

	data() {
		return {
			locationArgs: {},
			personaArgs: {},
			activityArgs: {},
		};
	},

	components: { PieChart },

	computed: {
		...mapGetters({
			state: "getAccount",
		}),
	},

	beforeMount() {
		let labels = [];
		let data = [];
		let colors = [];
		for (let i = 0; i < this.state.location_count.length; i++) {
			let location = this.state.location_count[i];
			let n = (Math.random() * 0xfffff * 1000000).toString(16);
			colors.push("#" + n.slice(0, 6));
			if (!location.city && !location.state && !location.country)
				labels.push("Empty Columns");
			else
				labels.push(
					location.city +
						", " +
						location.state +
						", " +
						location.country
				);

			data.push(location.count);
		}
		this.locationArgs = {
			labels,
			data,
			colors,
		};
		labels = [];
		data = [];
		colors = [];
		for (let i = 0; i < this.state.persona_count.length; i++) {
			let persona = this.state.persona_count[i];
			let n = (Math.random() * 0xfffff * 1000000).toString(16);
			colors.push("#" + n.slice(0, 6));
			if (!persona.jobLevel && !persona.jobFunction) labels.push("Empty Columns");
			else labels.push(persona.jobLevel + ", " + persona.jobFunction);
			data.push(persona.count);
		}
		this.personaArgs = {
			labels,
			data,
			colors,
		};
		labels = ["ad_clicks", "form_fills", "live_chats", "website_visits"];
		colors = [];
		for (let i = 0; i < 4; i++) {
			let n = (Math.random() * 0xfffff * 1000000).toString(16);
			colors.push("#" + n.slice(0, 6));
		}
		let activityCount = this.state.activity_count;
		data = [
			activityCount.ad_clicks,
			activityCount.form_fills,
			activityCount.live_chats,
			activityCount.website_visits,
		];
		this.activityArgs = {
			labels,
			data,
			colors,
		};
	},
};
</script>

<style>
#head {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
#info {
	padding-left: 30%;
	padding-right: 30%;
}
#charts {
	margin-top: 2%;
	display: flex;
	flex-direction: row;
	justify-content: space-around;
}
</style>
