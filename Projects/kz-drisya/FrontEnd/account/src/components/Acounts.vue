<template>
  <div id="app">
    <div>
      <label id="labe"> Search</label>
      <b-form-input
        id="input"
        placeholder=" search"
        v-model="searchVal"
      ></b-form-input>
      <br />
      <label id="labe">Rows per page </label>
      <b-form-input id="input" v-model="page_size"></b-form-input>
      <br />
    </div>
    <div class="overflow-auto">
      <Table v-bind:page_size="page_size" v-bind:accounts="accounts" />
      <b-pagination
        v-model="page"
        :per-page="page_size"
        :total-rows="1400"
        align="center"
      ></b-pagination>
    </div>
  </div>
</template>


<script>
import Table from "@/components/Table";
export default {
  components: {
    Table,
  },
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
      },
      searchVal: "",
      url: "",
      page: "1",
      page_size: "10",
      q: "",
    };
  },

  watch: {
    searchVal: {
      handler() {
        console.log("watching");
        this.splitVal();
        this.getParameter();
        this.getAccounts();
      },
      deep: true,
      immediate: true,
    },
    page: {
      handler() {
        this.getParameter();
        this.getAccounts();
      },
      depp: true,
      immediate: true,
    },
    page_size: {
      handler() {
        this.getParameter();
        this.getAccounts();
      },
      deep: true,
      immediate: true,
    },
  },
  mounted() {
    console.log("mounted");
    this.getParameter();
    this.getAccounts();
  },
  methods: {
    getKeyValue(pair) {
      if (!pair.includes(":")) this.q = pair.trim();
      else {
        let pair_split = pair.split(":");
        let key = pair_split[0];
        this.input[key] = pair_split[1];
      }
    },
    splitVal() {
      console.log("splitting");
      this.initial();
      let val = this.searchVal;
      let pairs;
      if (!val.includes(",")) {
        this.getKeyValue(val);
      } else {
        console.log("comma");
        pairs = val.split(",");
        for (let i in pairs) {
          this.getKeyValue(pairs[i], i);
        }
      }
    },

    initial() {
      (this.input.name = ""),
        (this.input.city = ""),
        (this.q = ""),
        (this.input.type = "");
      this.input.sfdc = "";
      this.input.domain = "";
      this.input.country = "";
      this.input.state = "";
      this.page = "1";
    },
    getParameter() {
      const url = `http://localhost:8080/accounts?metrics=all`;
      let requestParam = url + `&page=${this.page}&page_size=${this.page_size}`;
      if (this.input.name !== "") requestParam += `&name=${this.input.name}`;
      if (this.input.city !== "") requestParam += `&city=${this.input.city}`;
      if (this.input.state !== "") requestParam += `&state=${this.input.state}`;
      if (this.input.country !== "")
        requestParam += `&country=${this.input.country}`;
      if (this.input.type !== "") requestParam += `&type=${this.input.type}`;
      if (this.input.sfdc !== "") requestParam += `&sfdc=${this.input.sfdc}`;
      if (this.q !== "") requestParam += `&q=${this.q}`;
      if (this.input.domain !== "")
        requestParam += `&domain=${this.input.domain}`;

      this.url = requestParam;
    },
    getAccounts() {
      console.log(this.url);
      fetch(this.url, {
        method: "get",
      })
        .then((response) => {
          // console.log(response.json);
          return response.json();
        })
        .then((jsonResponse) => {
          return jsonResponse;
        })
        .then((jsonResponse) => {
          // console.log(jsonResponse);
          let x = jsonResponse;
          for (let i in x) {
            delete x[i]["id"];
            delete x[i]["empty"];
          }
          //console.log(jsonResponse);
          this.accounts = x;
        });
    },
  },
};
</script>

<style>
#app {
  margin-top: 60px;
  margin-left: 10px;
}
#input {
  width: 10%;
  height: 10%;
  border-left: 1px;
  margin-block: 5px;
}
#labe {
  font-size: 20px;
  font-weight: bold;
  color: black;
}
</style>