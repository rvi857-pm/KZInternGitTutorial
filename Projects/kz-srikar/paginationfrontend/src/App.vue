<template>
  <div id="app">
    <div v-if="isPaginated">
      <b-button @click="togglePagination">Remove Pagination</b-button>
      <b-container fluid>
        <b-pagination
          v-model="currentPage"
          :total-rows="totalResults"
          :per-page="perPage"
          aria-controls="my-table"
        ></b-pagination>
        <input v-model="perPage" type="number" :min="1" inline controls/>
      </b-container>
      <Page :pageSize="getPageSize()" :currentPage="currentPage" />
    </div>
    <div v-else>
      <b-button @click="togglePagination">Add Pagination</b-button>
      <b-table id="my-table" :items="results" :per-page="totalResults" small></b-table>
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
    };
  },
  methods: {
    togglePagination() {
      this.isPaginated = !this.isPaginated;
    },
    getPageSize() {
      if(this.perPage == "")
        return 0;
      return parseInt(this.perPage);
    }
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
  margin-top: 60px;
}
</style>
