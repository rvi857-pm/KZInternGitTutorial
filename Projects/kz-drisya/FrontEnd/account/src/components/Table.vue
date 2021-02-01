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
        <Chart v-bind:label="label" v-bind:dataset="dataset" />
      </div>

      <b-button class="mt-3" block @click="toggle">Close</b-button>
    </b-modal>
  </div>
</template>
<script>
import Chart from "@/utils/Piechart.js";
export default {
  components: {
    Chart,
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
      label: [],
      dataset: [],
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

      this.label = [];
      this.dataset = [];

      for (let i in record.location_count) {
        let x = record.location_count[i]["city"];
        x = x + "/" + record.location_count[i]["state"];
        this.label.push(x);
        this.dataset.push(record.location_count[i]["count"]);
      }
      this.details = !this.details;
      this.row = record;
    },
  },
};
</script>
<style >
</style>