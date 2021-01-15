<template>
  <div id="app">

    <router-view  :items="items"
                  :str="str"
                  :last="last"
                  :request='request'
                  :first='first'
                  :lastPage='lastPage'
                  :page='page'
                  :pageSize="pageSize"
                  :nextPage="nextPage"
                  :prevPage="prevPage"
                  :fPage="fPage"
                  :lPage="lPage"
                  :search="search"
                  />

  </div>

</template>

<script>

import axios from 'axios'

export default
{
  data() {
    return{
      items: [],
      str: '',
      last: false,
      first: false,
      lastPage: 0,
      page: 1

    }
  },
  mounted: function() {
      this.request("")
  },
  methods:{
    pageSize(size){
      this.request("?pageSize=" + size)
    },
    nextPage(){
      this.page++
      this.request("?page=" + (this.page - 1))
    },
    prevPage(){
      this.page--
      this.request("?page=" + (this.page - 1))
    },
    lPage(){
        this.page = this.lastPage
        this.request("?page=" + (this.page - 1))
    },
    fPage(){
        this.page = 1
        this.request("?page=" + (this.page - 1))
    },
    search(input){
      
      this.str = input
      this.request("?")
    },
    request(input){
      if(this.str === '' || this.str.endsWith('=')){
        axios.get("http://localhost:8080/accounts" + input)
        .then((jsonData) => {
        this.items = jsonData.data.content
        this.last = jsonData.data.last
        this.first = jsonData.data.first
        this.lastPage = jsonData.data.totalPages
        this.page = jsonData.data.number + 1
        })
      }
      else{
        axios.post("http://localhost:8080/accounts" + input + "&" + this.str)
        .then((jsonData) => {
        this.items = jsonData.data.content
        this.last = jsonData.data.last
        this.first = jsonData.data.first
        this.lastPage = jsonData.data.totalPages
        this.page = jsonData.data.number + 1
        })
      }
    }
  }
};


</script>

<style scoped>
</style>