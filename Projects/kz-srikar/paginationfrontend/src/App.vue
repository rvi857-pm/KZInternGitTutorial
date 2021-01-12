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
          :per-page="perPage"
          aria-controls="my-table"
        ></b-pagination>
      </b-col>
      <b-container fluid>
        <b-row>
          <b-col sm="3" class="pb-2">
            <label class="lab">Page Size</label><br/>
            <input class="in" v-model="perPage" type="number" :min="1" inline controls />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Account Name</label><br/>
            <input class="in" v-model="accountName" placeholder="Account Name" />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Ip Domain</label><br/>
            <input class="in" v-model="ipDomain" placeholder="Ip Domain" />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Ip Geo City</label><br/>
            <input class="in" v-model="ipGeoCity" placeholder="Ip Geo City" />
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="3" class="pb-2">
            <label class="lab">Ip Geo State</label><br/>
            <input class="in" v-model="ipGeoState" placeholder="Ip Geo State" />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Ip Geo Country</label><br/>
            <input class="in" v-model="ipGeoCountry" placeholder="Ip Geo Country" />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Type</label><br/>
            <input class="in" v-model="type" placeholder="Type" />
          </b-col>
          <b-col sm="3" class="pb-2">
            <label class="lab">Sfdc Account Id</label><br/>
            <input class="in" v-model="sfdcAccountId" placeholder="Sfdc Account Id" />
          </b-col>
        </b-row>
        <br />
      </b-container>
      <Page
        :pageSize="getPageSize()"
        :currentPage="currentPage"
        :accountName="accountName"
        :ipDomain="ipDomain"
        :ipGeoCity="ipGeoCity"
        :ipGeoState="ipGeoState"
        :ipGeoCountry="ipGeoCountry"
        :type="type"
        :sfdcAccountId="sfdcAccountId"
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

export default {
  name: "App",
  components: {
    Page,
  },
  data() {
    return {
      isPaginated: false,
      totalResults: 0,
      perPage: 10,
      currentPage: 1,
      results: [],
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
      if (this.perPage == "") return 0;
      return parseInt(this.perPage);
    },
    onChangeInput() {
      this.currentPage = 1;
    }
  },
  watch: {
        perPage: function() {
            this.onChangeInput();
        },
        accountName: function() {
            this.onChangeInput();
        },
        ipDomain: function() {
            this.onChangeInput();
        },
        ipGeoCity: function() {
            this.onChangeInput();
        },
        ipGeoState: function() {
            this.onChangeInput();
        },
        ipGeoCountry: function() {
            this.onChangeInput();
        },
        type: function() {
            this.onChangeInput();
        },
        sfdcAccountId: function() {
            this.onChangeInput();
        },
    },
  mounted: function () {
    fetch("http://localhost:8080/accounts/", {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((jsonResponse) => {
        this.totalResults = jsonResponse.length;
        this.results = jsonResponse;
        console.log(this.totalResults);
      });
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
</style>
