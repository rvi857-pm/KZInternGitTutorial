<template>
  <div>
    <label> search here</label>
    <b-form-input
      id="input"
      placeholder=" search here"
      v-model="input.searchVal"
    ></b-form-input>

    <div class="overflow-auto">
      <b-pagination
        v-model="input.page"
        :per-page="input.page_size"
        :total-rows="1400"
        align="center"
      ></b-pagination>
      <b-table
        hover
        small
        :items="accounts"
        :per-page="input.page_size"
        responsive
      ></b-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      accounts: [],
      input: {
        name: "",
        domain: "",
        city: "",
        state: "",
        country: "",
        type: "",
        sfdc: "",
        page: "1",
        page_size: "10",
        q: "",
        searchVal: "",
      },
    };
  },
  watch: {
    input: {
      handler() {
        console.log("watching");
        this.splitVal(this.input.searchVal);
        let url = this.getParameter(this.input);
        console.log(url);
        console.log(this.input);
        this.getAccounts(url);
      },
      deep: true,
      immediate: true,
    },
  },
  mounted() {
    let url = this.getParameter(this.input);
    // console.log(url);
    this.getAccounts(url);
    console.log(this.accounts);
  },
  methods: {
    splitVal(val) {
      this.initial();
      if (!val.includes("?")) {
        if (!val.includes(":")) this.input.q = val;
        else {
          let pair = val.split(":");
          let key = pair[0];
          this.input[key] = pair[1];
        }
      } else {
        console.log("hey");
        let x = val.split(",");
        for (let i in x) {
          if (!x[i].includes(":")) this.input.q = x[i];
          else {
            let pair = x[i].split(":");
            let key = pair[0];
            this.input[key] = pair[1];
          }
        }
      }
    },
    initial() {
      (this.input.name = ""), (this.input.city = ""), (this.input.q = "");
    },
    getParameter(account) {
      const url = `http://localhost:8080/accounts`;
      let requestParam =
        url + `?page=${account.page}&page_size=${account.page_size}`;
      if (account.name !== "") requestParam += `&name=${account.name}`;
      if (account.city !== "") requestParam += `&city=${account.city}`;
      if (account.state !== "") requestParam += `&state=${account.state}`;
      if (account.country !== "") requestParam += `&country=${account.country}`;
      if (account.type !== "") requestParam += `&type=${account.type}`;
      if (account.sfdc !== "") requestParam += `&sfdc=${account.sfdc}`;
      if (account.q !== "") requestParam += `&q=${account.q}`;
      if (account.domain !== "") requestParam += `&domain=${account.domain}`;

      return requestParam;
    },
    getAccounts(url) {
      fetch(url, {
        method: "get",
      })
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          return jsonResponse;
        })
        .then((jsonResponse) => {
          this.accounts = jsonResponse.content;
        });
    },
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
#input {
  width: 20%;
  height: 10%;
}
</style>