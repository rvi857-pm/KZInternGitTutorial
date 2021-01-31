<template>
  <div id ='t'>
    <b-table small sort-by="datetime" responsive=true hover :items="items">
      <template #cell(id)="data">
        <router-link :to="link + `${data.value}`">{{ data.value }}</router-link>
      </template>
    </b-table>
  </div>
</template>

<script>
  export default {
    props: [
      'items',
      'link'
    ],
  };
</script>

<style scoped>
</style>