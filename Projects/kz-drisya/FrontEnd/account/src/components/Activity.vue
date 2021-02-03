<template>
  <div>
    <b-button v-on:click="goback">goback</b-button>
    <p id="details">BUYER ID: {{ buyer.id }}</p>
    <h5 id="details">ACCOUNT NAME: {{ buyer.account_name }}</h5>
    <p id="details">CITY: {{ buyer.city }}</p>
    <p id="details">COUNTRY: {{ buyer.country }}</p>
    <p id="details">STATE: {{ buyer.state }}</p>
    <p id="details">JOB FUNCTION: {{ buyer.job_function }}</p>
    <p id="details">JOB LEVEL: {{ buyer.job_level }}</p>
    <p id="details">SOURCe: {{ buyer.source }}</p>

    <b-table striped hover :items="activity" :fields="column" small> </b-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      buyer: {},
      activity: [],
      column: [
        { activityType: "ACTIVITY TYPE" },
        { datetime: "TIMESTAMP" },
        { details: "Details" },
      ],
      locationCount: {},
      personCount: {},
      activityCount: {},
      record: {},
      id: "",
    };
  },
  mounted() {
    console.log(this.$route.params.locationCount);
    this.locationCount = this.$route.params.locationCount;
    this.record = this.$route.params.record;
    this.id = this.$route.params.id;
    this.personCount = this.$route.params.personCount;
    this.activityCount = this.$route.params.activityCount;
    let temp = this.$route.params.buyer;
    this.buyer = temp[0];
    this.activity = this.buyer.activity;
    for (let i in this.activity) {
      this.activity[i].datetime = new Date(+this.activity[i].datetime);
    }
    this.activity.sort((a, b) => (a.datetime > b.datetime ? 1 : -1));
  },
  methods: {
    goback: function (event) {
      console.log(event);
      this.$router.push({
        name: "buyer-router",
        params: {
          id: this.id,
          record: this.record,
          locationCount: this.locationCount,
          personCount: this.personCount,
          activityCount: this.activityCount,
        },
      });
    },
  },
};
</script>

<style >
</style>