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
      state: Object
    },

    data () {
      return {
        
      }
    },
    
    methods: {
      getUrl(){

      }
    },

    mounted() {
      let queryUrl = this.getUrl();
      let url = "http://localhost:8080/accounts";
      url += queryUrl ? "?" + queryUrl : ""; 
      axios.get(url)
        .then(res => {
          this.items = res.data;
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