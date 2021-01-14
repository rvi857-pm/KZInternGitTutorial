<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">@kz-srikar PAGINATION AND SEARCH</b-navbar-brand>
        <div v-if="isPaginated">
            <b-button size="sm" @click="togglePagination"
              >Remove Pagination</b-button
            >
        </div>
        <div v-else>
            <b-button size="sm" @click="togglePagination"
              >Add Pagination</b-button
            >
        </div>
      </b-navbar>
    </div>

    <div v-if="isPaginated">
      <b-col class="btn" sm="2">
        <b-pagination
          v-model="currentPage"
          :total-rows="totalResults"
          :per-page="pageSize"
          aria-controls="my-table"
        ></b-pagination>
      </b-col>
      <b-container fluid>
        <b-row>
          <b-col class="pb-1">
            <label class="lab pr-5">Page Size</label>
            <input v-model="pageSize" type="number" :min="1" inline controls />
          </b-col>
          <b-col  class="pb-2">
            <input class="in" v-model="name" placeholder="Search All" />
            <b-button @click="onClickAllSubmit" size="sm">Search All</b-button>
          </b-col>
        </b-row>
        <b-row>
          <b-col  class="pb-2">
            <label class="lab">Account Name</label><br/>
            <input class="in" v-model="accountName" placeholder="Account Name" />
          </b-col>
          <b-col class="pb-2">
            <label class="lab">Ip Domain</label><br/>
            <input class="in" v-model="ipDomain" placeholder="Ip Domain" />
          </b-col>
          <b-col class="pb-2">
            <label class="lab">Ip Geo City</label><br/>
            <input class="in" v-model="ipGeoCity" placeholder="Ip Geo City" />
          </b-col>
          <b-col  class="pb-2">
            <label class="lab">Ip Geo State</label><br/>
            <input class="in" v-model="ipGeoState" placeholder="Ip Geo State" />
          </b-col>
          <b-col  class="pb-2">
            <label class="lab">Ip Geo Country</label><br/>
            <input class="in" v-model="ipGeoCountry" placeholder="Ip Geo Country" />
          </b-col>
          <b-col  class="pb-2">
            <label class="lab">Type</label><br/>
            <input class="in" v-model="type" placeholder="Type" />
          </b-col>
          <b-col class="pb-2">
            <label class="lab">Sfdc Account Id</label><br/>
            <input class="in" v-model="sfdcAccountId" placeholder="Sfdc Account Id" />
          </b-col>
          <b-col class="pb-2 pt-4">
            <b-button @click="onClickSubmit" size="sm">Search</b-button>
          </b-col>
        </b-row>
        <br />
      </b-container>
      <Page
        :pageSize="getPageSize()"
        :results="results"
      />
    </div>
    <div v-else>
      <b-table
        id="my-table"
        :items="results"
        :per-page="totalResults"
        small
      ></b-table>
    </div>
  </div>
</template>

<script>
import Page from "./components/Page.vue";
import accountApi from './util/accountsApi';

export default {
  name: "App",
  components: {
    Page,
  },
  data() {
    return {
      isPaginated: false,
      isSearchAll: true,
      totalResults: 0,
      results: [],
      currentPage: 1,
      pageSize: 10,
      name: "",
      accountName: "",
      ipDomain: "",
      ipGeoCity: "",
      ipGeoState: "",
      ipGeoCountry: "",
      type: "",
      sfdcAccountId: "",
    };
  },
  methods: {
    togglePagination() {
      this.isPaginated = !this.isPaginated;
    },
    getPageSize() {
      if (this.pageSize == "") return 0;
      return parseInt(this.pageSize);
    },
    getAllSearch() {
      let obj = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        name: this.name
      };
      accountApi.getAllSearchAccounts(obj)
        .then(response => {
          this.totalResults = response.totalElements;
          this.results = response.content;
        })
    },
    onClickAllSubmit() {
      this.isSearchAll = true;
      this.getAllSearch();
      this.currentPage = 0;
    },
    getSpecificSearch() {
        let obj = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        accountName: this.accountName,
        ipDomain: this.ipDomain,
        ipGeoCity: this.ipGeoCity,
        ipGeoState: this.ipGeoState,
        ipGeoCountry: this.ipGeoCountry,
        type: this.type,
        sfdcAccountId: this.sfdcAccountId,
      };
      accountApi.getSpecificSearchAccounts(obj)
        .then(response => {
          this.totalResults = response.totalElements;
          this.results = response.content;
        })
    },
    onClickSubmit() {
      this.isSearchAll = false;
      this.getSpecificSearch();
      this.currentPage = 0;
    }
  },
  watch: {
        currentPage: function(newVal, oldVal) {
          if(newVal == 0){
            this.currentPage = 1;
            return;
          }
          if(oldVal == 0)
            return;

          if(this.isSearchAll){
              this.getAllSearch();
          }
          else{
            this.getSpecificSearch();
          }
          
        },
        pageSize: function() {
          if(this.isSearchAll){
              this.getAllSearch();
          }
          else{
            this.getSpecificSearch();
          }
        }

    },
  mounted: function () {
    accountApi.getAllAccounts()
        .then(response => {
          this.totalResults = response.length;
          this.results = response;
        })
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
}
.btn {
  padding: 20px;
  margin: 20px;
}
.lab {
  text-align: center;
  margin-top: 10px;
  color: rgb(190, 58, 102);
}
input {
  padding: 5px;
  margin-right: 10px;
}
</style>
