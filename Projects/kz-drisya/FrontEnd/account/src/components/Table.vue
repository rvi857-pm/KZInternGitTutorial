<template>
  <div>
    <b-table
      striped
      hover
      :items="accounts"
      :per-page="page_size"
      small
      @row-clicked="toggle"
    >
    </b-table>
    <b-modal v-model="details" id="bv-model-example" hide-footer>
      <template #modal-title>
        {{ row.name }}
      </template>
      <div class="d-block text-center">
        <p>Ip Domain : {{ row.ipDomain }}</p>
        <p>City : {{ row.city }}</p>
        <p>State : {{ row.state }}</p>
        <p>Country : {{ row.country }}</p>
        <p>Type : {{ row.type }}</p>
        <p>Salesforce Id : {{ row.salesforceId }}</p>
      </div>
      <b-button class="mt-3" block @click="toggle">Close</b-button>
    </b-modal>
  </div>
</template>
<script>
export default {
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
    };
  },
  methods: {
    toggle(record) {
      console.log(record);
      this.details = !this.details;
      this.row = record;
      console.log("hey");
      console.log(this.row);
      console.log("hey");
    },
  },
};
</script>
<style >
</style>