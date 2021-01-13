<template>
  <div class="overflow-auto">
    <b-table id="my-table" :items="results" :per-page="pageSize" small></b-table>
  </div>
</template>

<script>
export default {
  props: {
    pageSize: Number,
    results: Array
  },
  data() {
    return {
    };
  },
};
</script>