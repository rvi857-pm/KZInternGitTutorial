<template>
  <div id="app">
    <input v-model="account.account_name" type="text" />
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
      account: {
        account_name: null,
        ip_domain: null,
        ip_geo_city: null,
        ip_geo_state: null,
        ip_geo_country: null,
        sfdc: null,
        type: null,
        page_no: "1",
        page_size: "10",
      },
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
