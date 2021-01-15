<template>
  <div id ='t'>
    <b-table small responsive="lg" hover :items="items">
      <template #cell(name)="data">
        <router-link :to="`/single/${data.index}`">{{ data.value }}</router-link>
      </template>
    </b-table>
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