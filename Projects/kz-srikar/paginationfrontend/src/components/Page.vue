<template>
    <div class="table">
        <b-table
            id="my-table"
            :items="results"
            :per-page="pageSize"
            small
        ></b-table>
    </div>
</template>

<script>
export default {
    props: {
        pageSize: Number,
        results: Array
    },
    data() {
        return {};
    },
};
</script>

<style scoped>
.table {
    margin: 10px;
}
</style>
