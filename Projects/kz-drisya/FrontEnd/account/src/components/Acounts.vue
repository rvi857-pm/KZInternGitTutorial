<template>
  <div id="app">
    <Addacc />
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
      <b-table
        striped
        hover
        :items="accounts"
        :fields="column"
        :per-page="page_size"
        small
        @row-clicked="toggle"
      >
      </b-table>
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
import Addacc from "@/components/Addacc";
export default {
  components: {
    Addacc,
  },
  data() {
    return {
      datarecords: {},
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
      details: false,
      column: [
        { name: "NAME" },
        { ip_domain: "IP DOMAIN" },
        { city: "CITY" },
        { state: "STATE" },
        { country: "COUNTRY" },
        { type: "TYPE" },
        { salesforce_id: "SALESFORCE ID" },
      ],
      locationCount: {
        data: [],
        label: [],
      },
      personCount: {
        data: [],
        label: [],
      },
      activityCount: {
        data: [],
        label: ["Ad click", "Form fill", "Website visit", "Live chat"],
      },
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
    toggle(record) {
      // console.log(record);
      if (record == null) console.log("maaan");
      let label_l = [],
        label_p = [],
        dataset_l = [],
        dataset_p = [],
        dataset_a = [];

      for (let i in record.location_count) {
        // console.log(record);
        let x = record.location_count[i]["city"];
        x = x + "/" + record.location_count[i]["state"];
        label_l.push(x);
        dataset_l.push(record.location_count[i]["count"]);
        /** */
      }
      this.locationCount.label = label_l;
      this.locationCount.data = dataset_l;
      for (let i in record.persona_count) {
        let x =
          record.persona_count[i]["job_function"] +
          record.persona_count[i]["job_level"];
        label_p.push(x);
        dataset_p.push(record.persona_count[i]["count"]);
      }
      this.personCount.label = label_p;
      this.personCount.data = dataset_p;
      dataset_a.push(record.activity_count["ad_clicks"]);
      dataset_a.push(record.activity_count["form_fills"]);
      dataset_a.push(record.activity_count["website_visits"]);
      dataset_a.push(record.activity_count["live_charts"]);
      this.activityCount.data = dataset_a;
      this.datarecords = record;
      //  console.log(this.locationCount);
      this.$router.push({
        name: "buyer-router",
        params: {
          id: record.name,
          record: this.datarecords,
          locationCount: this.locationCount,
          personCount: this.personCount,
          activityCount: this.activityCount,
        },
      });
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