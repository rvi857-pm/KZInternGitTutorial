<template>
  <div id="table">
    <div class="overflow-auto">
      <h1>Account</h1>
      <b-table
        hover
        small
        :items="friends"
        :per-page="account.page_size"
        responsive
      ></b-table>

      <b-pagination
        v-model="account.page_no"
        :per-page="account.page_size"
        :total-rows="1400"
        align="center"
      ></b-pagination>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    account: Object,
  },
  data() {
    return {
      friends: [],
    };
  },
  methods: {
    getUrl() {
      let requestParam = `http://localhost:8080/accounts`;
      requestParam += `?page=${this.account.page_no}&page_size=${this.account.page_size}`;
      if (this.account.name !== "")
        requestParam += `&name=${this.account.name}`;
      if (this.account.domain !== "")
        requestParam += `&domain=${this.account.domain}`;
      if (this.account.city !== "")
        requestParam += `&city=${this.account.city}`;
      if (this.account.state !== "")
        requestParam += `&state=${this.account.state}`;
      if (this.account.country !== "")
        requestParam += `&country=${this.account.country}`;
      if (this.account.sfdc !== "")
        requestParam += `&sfdc=${this.account.sfdc}`;
      if (this.account.type !== "")
        requestParam += `&type=${this.account.type}`;
      if (this.account.q !== "") requestParam += `&q=${this.account.q}`;
      console.log(this.account);
      console.log(requestParam);
      return requestParam;
    },
    callApi() {
      let url = this.getUrl();
      fetch(url, { method: "get" })
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          this.friends = jsonResponse;
        });
    },
  },
  watch: {
    account: {
      handler() {
        this.callApi();
      },
      deep: true,
      immediate: true,
    },
  },
  mounted: function () {
    this.callApi();
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>