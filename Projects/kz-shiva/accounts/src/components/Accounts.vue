<template>
  <div>
    <b-table
      id="my-table"
      :items="items"
      :fields="fields"
    ></b-table>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    
    props: {
      fields: Array,
      items: Array,
      state: Object,
      updateItems: Function
    },

    data () {
      return {
        
      }
    },
    
    methods: {
      getUrl(){
        let url = "";
        url += url ? this.state.accountName ? "&name=" + this.state.accountName : "" : this.state.accountName ? "name=" + this.state.accountName : "";
        url += url ? this.state.ipDomain ? "&domain=" + this.state.ipDomain : "" : this.state.ipDomain ? "domain=" + this.state.ipDomain : "";
        url += url ? this.state.ipGeoCity ? "&city=" + this.state.ipGeoCity : "" : this.state.ipGeoCity ? "city=" + this.state.ipGeoCity : "";
        url += url ? this.state.ipGeoState ? "&state=" + this.state.ipGeoState : "" : this.state.ipGeoState ? "state=" + this.state.ipGeoState : "";
        url += url ? this.state.ipGeoCountry ? "&country=" + this.state.ipGeoCountry : "" : this.state.ipGeoCountry ? "country=" + this.state.ipGeoCountry : "";
        url += url ? this.state.type ? "&type=" + this.state.type : "" : this.state.type ? "type=" + this.state.type : "";
        url += url ? this.state.sfdcAccountId ? "&id=" + this.state.sfdcAccountId : "" : this.state.sfdcAccountId ? "id=" + this.state.sfdcAccountId : "";
        return url;
      }
    },

    mounted() {
      let queryUrl = this.getUrl();
      let url = "http://localhost:8080/accounts";
      url += queryUrl ? "?" + queryUrl : "";

      axios.get(url)
        .then(res => {
          this.updateItems(res.data);
        })
        .catch(err => {
          console.log(err);
          return [];
        })

    }
  }
</script>

<style>

</style>