<template>
  <div id="app">
    <Header />
    
    <b-container>
    <Data :items="item" />
    </b-container>

  </div>

</template>

<script>
import Data from '../components/data.vue'
import Header from '../components/headerType2.vue'

export default
{
    data(){
        return{
            item: []
        }
    },
    props: [
        'items'
    ],
    components: { 
        Data,
        Header
    },
    mounted: function(){
        this.item.push(this.items[this.$route.params.id])
    }
};
</script>
