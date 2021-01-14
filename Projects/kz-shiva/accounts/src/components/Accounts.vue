<template>
  <div>
    <b-pagination id="page"
      v-if="flag"
      v-model="page"
      :total-rows="len"
      :per-page="perPage"
      aria-controls="my-table"
      align="center"
    ></b-pagination>
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
        page: 0,
        len: 0,
        perPage: 0,
        flag:false
      }
    },
    
    watch: {
     page: function () {
      let queryUrl = this.getUrl();
      let url = "http://localhost:8080/accounts";
      url += queryUrl ? "?" + queryUrl : "";
      
      axios.get(url)
        .then(res => {
          this.updateItems(res.data.content);
        })
        .catch(err => {
          this.flag = false;
          console.log(err);
        })
    }
  },
    
    methods: {

      updateLen(val){
        this.len = val
      },

      updatePerPage(val){
        this.perPage = val
      },

      updatePage(val){
        this.page = val
      },

      getUrl(){
        let url = "";
        url += url ? this.state.accountName ? "&name=" + this.state.accountName : "" : this.state.accountName ? "name=" + this.state.accountName : "";
        url += url ? this.state.ipDomain ? "&domain=" + this.state.ipDomain : "" : this.state.ipDomain ? "domain=" + this.state.ipDomain : "";
        url += url ? this.state.ipGeoCity ? "&city=" + this.state.ipGeoCity : "" : this.state.ipGeoCity ? "city=" + this.state.ipGeoCity : "";
        url += url ? this.state.ipGeoState ? "&state=" + this.state.ipGeoState : "" : this.state.ipGeoState ? "state=" + this.state.ipGeoState : "";
        url += url ? this.state.ipGeoCountry ? "&country=" + this.state.ipGeoCountry : "" : this.state.ipGeoCountry ? "country=" + this.state.ipGeoCountry : "";
        url += url ? this.state.type ? "&type=" + this.state.type : "" : this.state.type ? "type=" + this.state.type : "";
        url += url ? this.state.sfdcAccountId ? "&id=" + this.state.sfdcAccountId : "" : this.state.sfdcAccountId ? "id=" + this.state.sfdcAccountId : "";
        if(this.page == 0){
          url += url ? this.state.page ? "&page=" + this.state.page : "" : this.state.page ? "page=" + this.state.page : "";  
        }else{
          url += url ? "&page=" + (this.page - 1).toString() : "page=" + (this.page - 1).toString();
        }
        url += url ? this.state.pageSize ? "&page_size=" + this.state.pageSize : "" : this.state.pageSize ? "page_size=" + this.state.pageSize : "";
        return url;
      }
    },

    mounted() {

      let queryUrl = this.getUrl();
      let url = "http://localhost:8080/accounts";
      url += queryUrl ? "?" + queryUrl : "";
      
      axios.get(url)
        .then(res => {
          if(this.state.page) {
            this.updateItems(res.data.content);
            if(this.state.pageSize) this.updatePerPage(Number(this.state.pageSize));
            else this.updatePerPage(10);
            this.updateLen(res.data.totalElements);
            console.log(this.len);
            this.updatePage(Number(this.state.page) + 1); 
            this.flag = true
          }
          else{
            this.updateItems(res.data);
            this.flag = false
          }
        })
        .catch(err => {
          console.log(err);
        })

    }
  }
</script>

<style>
#page{
  margin-top: 20px
}
</style>