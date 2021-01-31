import { Pie } from './BaseCharts'

export default {
    extends: Pie,
    props: {
        info: Object
    },
    mounted() {
        this.renderChart({
            labels: this.info.labels,
            datasets: [
                {
                    backgroundColor: this.info.colors,
                    data: this.info.data
                }
            ]
        }, { responsive: true, maintainAspectRatio: false })
    }
}