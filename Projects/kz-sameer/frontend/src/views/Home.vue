<template>
  <div id="app">
    <Header :search="search" />

    <b-container>
    <Comp :items = "items" id='t' />
    </b-container>
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
import Comp from '../components/comp.vue';
import Nav from '../components/nav.vue'
import Header from '../components/header.vue'

export default
{
  components: { 
    Comp,
    Nav,
    Header
   },
   props: [
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
        'search'
   ]
};


</script>

<style scoped>
</style>