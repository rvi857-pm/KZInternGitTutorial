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
    data () {
      return {
        
      }
    },
    methods: {

    },
    mounted() {
      axios.get('http://localhost:8080/accounts')
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