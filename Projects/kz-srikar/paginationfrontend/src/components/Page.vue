<template>
  <div class="overflow-auto">
    <b-table id="my-table" :items="data" :per-page="pageSize" small></b-table>
  </div>
</template>

<script>
export default {
  props: {
    pageSize: Number,
    currentPage: Number,
  },
  data() {
    return {
      data: [],
    };
  },
  watch: {
    $props: {
      handler() {
          console.log("props change");
        fetch(
        `http://localhost:8080/accounts?page=${this.currentPage}&page_size=${this.pageSize}`,
        {
          method: "get",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          this.data = jsonResponse;
        });
      },
      deep: true,
      immediate: true,
    }
  },
  mounted: function () {
      console.log("mounted")
      fetch(
        `http://localhost:8080/accounts?page=${this.currentPage}&page_size=${this.pageSize}`,
        {
          method: "get",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          this.data = jsonResponse;
        });
  },
};
</script>