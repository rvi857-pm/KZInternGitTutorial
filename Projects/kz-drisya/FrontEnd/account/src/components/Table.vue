<template>
  <div>
    <b-table
      striped
      hover
      :items="accounts"
      :per-page="page_size"
      :fields="column"
      small
      @row-clicked="toggle"
    >
    </b-table>
    <b-modal v-model="details" id="bv-model-example" hide-footer>
      <template #modal-title>
        {{ row.name }}
      </template>
      <div class="d-block text-center">
        <p>Ip Domain : {{ row.ip_domain }}</p>
        <p>City : {{ row.city }}</p>
        <p>State : {{ row.state }}</p>
        <p>Country : {{ row.country }}</p>
        <p>Type : {{ row.type }}</p>
        <p>Salesforce Id : {{ row.salesforce_id }}</p>
        <p>Marketing qualified: {{ row.marketing_qualified }}</p>
        <pie-chart :data="chartData" :options="chartOptions"></pie-chart>
      </div>

      <b-button class="mt-3" block @click="toggle">Close</b-button>
    </b-modal>
  </div>
</template>
<script>
import PieChart from "@/utils/Piechart.js";
export default {
  components: {
    PieChart,
  },
  props: {
    accounts: Array,
    page_size: String,
  },
  data() {
    return {
      details: false,
      row: {
        name: "",
        city: "",
        state: "",
        country: "",
        type: "",
        domain: "",
        sfdc: "",
      },
      chartOptions: {
        hoverBorderWidth: 2,
      },
      chartData: {
        labels: ["Green", "Red", "Blue"],
        datasets: [
          {
            label: "Data One",
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
            data: [1, 10, 5],
          },
        ],
      },
      column: [
        { name: "NAME" },
        { ip_domain: "IP DOMAIN" },
        { city: "CITY" },
        { state: "STATE" },
        { country: "COUNTRY" },
        { type: "TYPE" },
        { salesforce_id: "SALESFORCE ID" },
      ],
    };
  },
  methods: {
    toggle(record) {
      //  console.log(this.record.location_count);

      let label = [];
      let dataset = [];
      //let color = [];

      //  let colour = "#41B883";
      for (let i in record.location_count) {
        let x = record.location_count[i]["city"];
        x = x + "/" + record.location_count[i]["state"];
        //  colour[6] = i;
        label.push(x);
        dataset.push(record.location_count[i]["count"]);
      }
      this.chartData.labels = label;
      this.chartData.datasets[0].data = dataset;
      console.log(this.chartData.datasets[0].data);
      this.details = !this.details;
      this.row = record;
    },
  },
};
</script>
<style >
</style>