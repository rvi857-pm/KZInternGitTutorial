<template>
  <div id="table">
    <div class="overflow-auto">
      <b-pagination
        v-model="account.page_no"
        :per-page="account.page_size"
        :total-rows="1400"
        align="center"
      ></b-pagination>
      <b-table
        hover
        small
        :items="friends"
        :per-page="account.page_size"
        responsive
      ></b-table>
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
    getRequest() {
      let requestParam = `?page=${this.account.page_no}&page_size=${this.account.page_size}`;
      if (this.account.account_name !== "")
        requestParam += `&name=${this.account.account_name}`;
      if (this.account.ip_geo_city !== "")
        requestParam += `&city=${this.account.ip_geo_city}`;
      if (this.account.ip_geo_state !== "")
        requestParam += `&state=${this.account.ip_geo_state}`;
      if (this.account.ip_geo_country !== "")
        requestParam += `&country=${this.account.ip_geo_country}`;

      console.log(this.account);
      console.log(requestParam);

      fetch(`http://localhost:8080/accounts${requestParam}`, {
        method: "get",
      })
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
        this.getRequest();
      },
      deep: true,
      immediate: true,
    },
  },
  mounted: function () {
    fetch(`http://localhost:8080/accounts?page=1&page_size=10`, {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((jsonResponse) => {
        this.friends = jsonResponse;
      });
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