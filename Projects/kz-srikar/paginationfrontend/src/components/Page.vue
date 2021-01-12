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
    accountName: String,
    ipDomain: String,
    ipGeoCity: String,
    ipGeoState: String,
    ipGeoCountry: String,
    type: String,
    sfdcAccountId: String,
  },
  data() {
    return {
      data: [],
    };
  },
  watch: {
    $props: {
      handler() {
        let reqParams = `page=${this.currentPage}&page_size=${this.pageSize}`;
        if(this.accountName != "")
            reqParams = reqParams + `&account_name=${this.accountName}`;
        if(this.ipDomain != "")
            reqParams = reqParams + `&ip_domain=${this.ipDomain}`;
        if(this.ipGeoCity != "")
            reqParams = reqParams + `&ip_geo_city=${this.ipGeoCity}`;
        if(this.ipGeoState != "")
            reqParams = reqParams + `&ip_geo_state=${this.ipGeoState}`;
        if(this.ipGeoCountry != "")
            reqParams = reqParams + `&ip_geo_country=${this.ipGeoCountry}`;
        if(this.type != "")
            reqParams = reqParams + `&type=${this.type}`;
        if(this.sfdcAccountId != "")
            reqParams = reqParams + `&sfdc_account_id=${this.sfdcAccountId}`;
        fetch(
          `http://localhost:8080/accounts?${reqParams}`,
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
    },
  },
  mounted: function () {
    console.log("mounted");
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