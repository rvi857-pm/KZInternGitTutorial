import { Pie } from "vue-chartjs";
//import { Pie } from './BaseCharts'
export default {
    extends: Pie,
    props: ["data", "options"],
    mounted() {
        // this.chartData is created in the mixin.
        // If you want to pass options please create a local options object
        this.renderChart(this.data, {
            borderWidth: "1px",
            hoverBackgroundColor: "red",
            hoverBorderWidth: "1px"
        });
    }
};
