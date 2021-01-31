<template>
  <div id="app">
    <Header :load="load" />
    
    <b-container>
    <Data :items="item" />

    <doughnut v-if="loaded"
                :obj="activityObj"
             />

    </b-container>
    <p>
    </p>
    <Comp v-if="loaded"
            :items="activities"
            :link="link" />

    <Nav :page = "page"
        :pageSize="pageSize"
        :nextPage="nextPage"
        :prevPage="prevPage"
        :last="last"
        :first="first"
        :fPage="fPage"
        :lPage="lPage"
        :size="size" />
  </div>

</template>

<script>
import Data from '../components/data.vue'
import Header from '../components/header.vue'
import axios from 'axios'
import Doughnut from '../components/doughnet.vue'
import Comp from '../components/comp.vue'
import Nav from '../components/nav.vue'

export default
{
    data(){
        return{
            item: [],
            loaded: false,
            boolPersona: false,
            boolLocation: false,
            boolActivity: false,
            activityObj: {},
            locationObj: {},
            personaObj: {},
            page: 1,
            last: false,
            first: true,
            lastPage: 0,
            activities: [],
            size: 10,
            link: "/activity/",
            load: false
        }
    },
    components: { 
        Data,
        Header,
        Doughnut,
        Comp,
        Nav
    },
    mounted: function(){

        axios.get("http://localhost:8080/buyers?metrics=all&id=" + this.$route.params.id )
        .then((jsonData) => {
            this.item.push(jsonData.data.content[0])
            this.activityObj = jsonData.data.content[0].activityCount
            delete this.activityObj.total
            delete this.item[0].activityCount
            this.loaded = true
        })
        this.request("")


    },
    methods:{
        pageSize(size){
            this.size = size
            this.request("?page=0&pageSize=" + size)
        },
        nextPage(){
            this.page++
            this.request("page=" + (this.page - 1) + "&pageSize=" + this.size)
        },
        prevPage(){
            this.page--
            this.request("page=" + (this.page - 1)+ "&pageSize=" + this.size)
        },
        lPage(){
            this.page = this.lastPage
            this.request("page=" + (this.page - 1)+ "&pageSize=" + this.size)
        },
        fPage(){
            this.page = 1
            this.request("page=" + (this.page - 1)+ "&pageSize=" + this.size)
        },
        request(input){
            if(input === '' || input.endsWith('=')){
                axios.get("http://localhost:8080/activites?buyer_id=" + this.$route.params.id)
                .then((jsonData) => {
                this.activities = jsonData.data.content
                this.last = jsonData.data.last
                this.first = jsonData.data.first
                this.lastPage = jsonData.data.totalPages
                this.page = jsonData.data.number + 1
                })
            }
            else{
                axios.get("http://localhost:8080/buyers?buyer_id=" + this.$route.params.id + "&" + input)
                .then((jsonData) => {
                this.activities = jsonData.data.content
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
