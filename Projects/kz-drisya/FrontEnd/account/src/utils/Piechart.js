import { Pie } from "vue-chartjs";
export default {
    extends: Pie,
    props: {
        label: Array,
        dataset: Array
    },
    mounted() {
        console.log("chart");
        console.log(this.dataset);
        this.renderChart({
            labels: this.label,
            datasets: [
                {
                    backgroundColor: this.dataset,
                    data: this.dataset
                }
            ]
        }, { responsive: true, maintainAspectRatio: false })
    }
}