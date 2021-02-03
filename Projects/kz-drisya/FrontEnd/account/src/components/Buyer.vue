<template>
  <div>
    <b-button v-on:click="goback">goback</b-button>
    <h1 id="details">{{ record.name }}</h1>
    <p style="text-align: center">IP DOMAIN: {{ record.ip_domain }}</p>
    <p style="text-align: center">CITY: {{ record.city }}</p>
    <p style="text-align: center">STATE: {{ record.state }}</p>
    <p style="text-align: center">COUNRTY: {{ record.country }}</p>
    <p style="text-align: center">TYPE: {{ record.type }}</p>
    <p style="text-align: center">Saleforce ID{{ record.salesforce_id }}</p>
    <p style="text-align: center">SCORE: {{ record.score }}</p>
    <p style="text-align: center">
      Marketing qualified: {{ record.marketing_qualified }}
    </p>

    <PieChart :data="1" />
    <PieChart :data="2" />
    <PieChart :data="3" />

    <b-table
      striped
      hover
      :items="buyers"
      :fields="column"
      small
      @row-clicked="toggle"
    >
    </b-table
    >-
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
      record: {},
      chartOptions: {
        hoverBorderWidth: 2,
      },
      locationCount: {},
      buyers: [],
      column: [
        { job_level: "JOB LEVEL" },
        { job_function: "JOB FUNCTION" },
        { city: "CITY" },
        { state: "STATE" },
        { country: "COUNTRY" },
        { source: "SOURCE" },
      ],
      details: false,
    };
  },
  mounted() {
    if (this.$route.params.record === null) console.log("OH..man");
    this.record = this.$route.params.record;
    this.getBuyers(this.record.name);
  },
  methods: {
    getBuyers(name) {
      const url = `http://localhost:8080/buyer?account_name=${name}`;
      fetch(url, {
        method: "get",
      })
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          this.buyers = jsonResponse;
        });
    },
    goback: function (event) {
      console.log(event);
      this.$router.push({
        name: "account-router",
      });
    },
    toggle() {
      this.details = !this.details;

      this.$router.push({
        name: "activity-router",
        params: {
          buyer: this.buyers,
          record: this.$route.params.record,
          locationCount: this.$route.params.locationCount,
          personCount: this.$route.params.personCount,
          activityCount: this.$route.params.activityCount,
          id: this.$route.params.id,
        },
      });
    },
  },
};
</script>
<style >
#details {
  display: flex;
  justify-content: center;
  align-items: center;
  color: Black;
  font-weight: bold;
}
</style>