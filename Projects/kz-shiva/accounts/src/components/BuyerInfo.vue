<template>
	<div id="head">
		<div id="info">
			<b-list-group>
				<b-list-group-item>account_id : {{state.account_id}}</b-list-group-item>
                <b-list-group-item>account_name : {{state.account_name}}</b-list-group-item>
                <b-list-group-item>city : {{state.city}}</b-list-group-item>
                <b-list-group-item>state : {{state.state}}</b-list-group-item>
                <b-list-group-item>country : {{state.country}}</b-list-group-item>
                <b-list-group-item>job_level : {{state.job_level}}</b-list-group-item>
                <b-list-group-item>job_function : {{state.job_function}}</b-list-group-item>
                <b-list-group-item>source : {{state.source}}</b-list-group-item>
                <b-list-group-item>score : {{state.score}}</b-list-group-item>
                <b-list-group-item>marketing_qualified : {{state.marketing_qualified}}</b-list-group-item>
			</b-list-group>
		</div>
        <div id="charts">
			<div>
				<h3 style="text-align:center;">Activity Count</h3>
				<pie-chart :args="activityArgs" />
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
import PieChart from './PieChart.vue';
export default {
	name: "buyer-info",

    components: {PieChart},
    
    data() {
		return {
			activityArgs: {},
		};
	},

    computed:{
        ...mapGetters({
            state: 'getBuyer'
        })
    },

    beforeMount() {
		let labels = ["ad_clicks", "form_fills", "live_chats", "website_visits"];
		let colors = [];
		for (let i = 0; i < 4; i++) {
			let n = (Math.random() * 0xfffff * 1000000).toString(16);
			colors.push("#" + n.slice(0, 6));
		}
		let activityCount = this.state.activity_count;
		let data = [
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
#info{
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
