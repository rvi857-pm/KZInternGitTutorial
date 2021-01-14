<template>
  <div id ='t'>
    <b-table small responsive="lg" hover :items="items"></b-table>
  </div>
</template>

<script>
  export default {
    props: [
      'items'
    ],
  };
</script>

<style scoped>
</style>