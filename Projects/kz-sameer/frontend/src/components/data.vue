<template>
  <div>
    <b-table stacked :items="items"></b-table>
  </div>
</template>

<script>
  export default {
      props: [
          'items'
      ]
  }
</script>