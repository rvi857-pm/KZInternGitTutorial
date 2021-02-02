<script>
import {Pie} from '@/util/Charts'

export default {

	name: "pie-chart",
    extends: Pie,
    props: {
        args: Object
    },
    mounted() {
        this.renderChart({
            labels: this.args.labels,
            datasets: [
                {
                    backgroundColor: this.args.colors,
                    data: this.args.data
                }
            ]
        }, { responsive: true, maintainAspectRatio: false })
    }
};
</script>

<style>
#pie {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
</style>
