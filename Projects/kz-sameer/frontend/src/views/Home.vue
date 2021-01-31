<template>
  <div id="app">
    
    <Header :search="search"
            :load="load"
             />
    <p></p>

    <file-upload/>

    <p></p>

    <Comp :items="items"
          :link="link"
           id='t' />

    <b-container>
    <Nav :page = "page"
        :pageSize="pageSize"
        :nextPage="nextPage"
        :prevPage="prevPage"
        :last="last"
        :first="first"
        :fPage="fPage"
        :lPage="lPage"
        :size="size" />
    </b-container>
  </div>

</template>

<script>
import Comp from '../components/comp.vue';
import Nav from '../components/nav.vue'
import Header from '../components/header.vue'
import FileUpload from '../components/fileUpload.vue';

export default
{
  components: { 
    Comp,
    Nav,
    Header,
    FileUpload
   },
   props:[
        'items',
        'request',
        'str',
        'last',
        'first',
        'lastPage',
        'page',
        'pageSize',
        'nextPage',
        'prevPage',
        'lPage',
        'fPage',
        'search',
        'size',
        'link',
        'load'
   ]
};


</script>

<style scoped>
</style>