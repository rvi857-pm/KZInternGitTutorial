<template>
  <div id="app">
    <b-form-row>
      <label id="label">Account name</label><br />
      <input id="input" v-model="account.name" />
      <label id="label"> Ip domain</label><br />
      <input id="input" v-model="account.domain" />
      <label id="label">City</label><br />
      <input id="input" v-model="account.city" />
    </b-form-row>
    <br />
    <b-form-row>
      <label id="label">State </label><br />
      <input id="input" v-model="account.state" />
      <label id="label"> Country</label><br />
      <input id="input" v-model="account.country" />
      <label id="label">SFDC ID</label><br />
      <input id="input" v-model="account.sfdc" />
      <label id="label">Type</label><br />
      <input id="input" v-model="account.type" />
    </b-form-row>

    <br />
    <b-form-row>
      <label id="label">Page Size </label><br />
      <input id="input" v-model="account.page_size" />
      <label id="label"> Search All</label><br />
      <input id="input" v-model="account.q" />
    </b-form-row>
    <label> page size </label>

    <Account v-bind:account="account" />
  </div>
</template>
<script>
import Account from "./components/Acounts";
export default {
  name: "App",
  components: {
    Account,
  },
  methods: {},
  data() {
    return {
      msg: "hey",
      didSearch: "",
      account: {
        name: "",
        domain: "",
        city: "",
        state: "",
        country: "",
        type: "",
        sfdc: "",
        page_no: "1",
        page_size: "10",
        q: "",
      },
    };
  },
  watch: {
    account: {
      handler() {
        this.page_no = "1";
      },
      deep: true,
    },
  },
};
</script>

<style>
#input {
  width: 19%;
  height: 30%;
}
#label {
  padding-left: 1%;
  font-size: 150%;
  color: green;
}
</style>
