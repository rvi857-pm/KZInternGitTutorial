import { Pie } from "vue-chartjs";
//import { Pie } from './BaseCharts'
export default {
    extends: Pie,
    props: {
        data: Number,
    },
    data() {
        return {
            d: {}
        };
    },
    mounted() {
        //        let locationCount = 
        //      this.call(locationCount);
        // let personCount = this.$route.params.personCount;
        //this.call(locationCount);
        // console.log(this.data);
        if (this.data === 1)
            this.call(this.$route.params.locationCount);
        else if (this.data === 2)
            this.call(this.$route.params.personCount);
        else
            this.call(this.$route.params.activityCount);
        //   console.log(this.$route.params.locationCount);

    },
    watch: {
        data: {
            handler() {
                console.log("changed");
                this.record = this.data;
            }
        }, deep: true,
        immediate: true
    },
    methods: {
        call(x) {
            console.log(x);
            this.renderChart({
                labels: x.label,
                datasets: [{
                    backgroundColor: [
                        "#0074D9",
                        "#FF4136",
                        "#2ECC40",
                        "#FF851B",
                        "#7FDBFF",
                        "#B10DC9",
                        "#FFDC00",
                        "#001f3f",
                        "#39CCCC",
                        "#01FF70",
                        "#85144b",
                        "#F012BE",
                        "#3D9970",
                        "#111111",
                        "#AAAAAA",
                    ],
                    data: x.data
                }]
            }, { responsive: true, maintainAspectRatio: false })
        }
    }
};
