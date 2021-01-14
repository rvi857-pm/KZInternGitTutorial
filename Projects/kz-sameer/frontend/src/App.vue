<template>
  <div id="app">
    <Header :search="search" />

    <Comp :items = items id='t' />
    
    <Nav :page = "page"
        :pageSize="pageSize"
        :nextPage="nextPage"
        :prevPage="prevPage"
        :last="last"
        :first="first"
        :fPage="fPage"
        :lPage="lPage" />
  </div>

</template>

<script>
import Comp from './components/comp.vue';
import Nav from './components/nav.vue'
import Header from './components/header.vue'
import axios from 'axios'

export default
{
  components: { 
    Comp,
    Nav,
    Header
   },
  name: 'app',
  data() {
  return {
    str: '',
    last: false,
    first: false,
    lastPage: 0,
    page: 1,
    items: [
      {
        a : 10,
        b : 20
      }
    ]
  }
  },
  mounted: function() {
    axios.get("http://localhost:8080/accounts")
      .then((jsonData) => {
        this.items = jsonData.data.content
        this.last = jsonData.data.last
        this.first = jsonData.data.first
        this.lastPage = jsonData.data.totalPages
        this.page = jsonData.data.number + 1
      })
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
#t{
  margin: 1% 1%;
}
</style>