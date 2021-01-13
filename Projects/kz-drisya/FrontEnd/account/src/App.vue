<template>
  <div id="app">
    <b-form-input
      id="input"
      align="center"
      placeholder="search"
      v-model="didSearch"
    ></b-form-input>
    <br />
    <label> page size </label>
    <b-form-input
      id="input"
      placeholder="page size"
      v-model="account.page_size"
    ></b-form-input>

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
        account_name: "",
        ip_geo_city: "",
        ip_geo_state: "",
        ip_geo_country: "",
        page_no: "1",
        page_size: "10",
      },
    };
  },
  watch: {
    didSearch: {
      handler() {
        this.account.account_name = this.didSearch;
        this.account.ip_geo_city = this.didSearch;
        this.account.ip_geo_state = this.didSearch;
        this.account.ip_geo_country = this.didSearch;
      },
    },
  },
};
</script>

<style>
#input {
  width: 20%;
  height: 10%;
}
</style>
