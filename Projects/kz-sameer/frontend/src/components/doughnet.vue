<script>
  import { Doughnut } from 'vue-chartjs'

  export default {
    extends: Doughnut,
    data(){
        return{
            chartData: {
                labels: [],
                datasets: [{
                    borderWidth: 1,
                    backgroundColor:  [],
                    data: []
                    }]
            },
            options: {
                legend: {
                    display: true
                },
                responsive: true,
                maintainAspectRatio: false
            },
            colors: ['rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(153, 102, 150, 1)',
                    'rgba(103, 102, 255, 1)',
                    'rgba(153, 172, 255, 1)',]
        }
    },
    props:{
        obj:{
            type: Object
        }
    },
    mounted: function(){
        this.chartData.labels = Object.keys(this.obj)
        this.chartData.datasets[0].data = Object.values(this.obj)
        for(var x = 0; x < this.chartData.labels.length - 8; x++){
            this.colors.push(this.colourGenerate())
        }
        this.chartData.datasets[0].backgroundColor = this.colors
        this.renderChart(this.chartData, this.options)
    },
    methods:{
        colourGenerate(){
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgba(" + r + "," + g + "," + b + ", 1)";
        }
    }

  };
</script>