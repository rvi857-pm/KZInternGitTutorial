<template>
    <div class="overflow-auto">
        <b-table
            striped
            hover
            :fields="fields"
            :items="results"
            :per-page="pageSize"
            small
            @row-clicked="myRowClickHandler"
        >
        </b-table>
    </div>
</template>

<script>
export default {
    props: {
        pageSize: Number,
        results: Array,
        fields: Array,
        myRowClickHandler: Function,
    },
    data() {
        return {};
    },
};
</script>

<style scoped></style>
