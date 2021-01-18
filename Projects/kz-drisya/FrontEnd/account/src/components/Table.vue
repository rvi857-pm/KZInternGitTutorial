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
    <b-modal v-model="details" :title="row.name">
      <p class="my-4">Ip Domain : {{ row.ipDomain }}</p>
      <p class="my-4">City : {{ row.city }}</p>
      <p class="my-4">State : {{ row.state }}</p>
      <p class="my-4">Country : {{ row.country }}</p>
      <p class="my-4">Salesforce Id : {{ row.salesforceId }}</p>
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