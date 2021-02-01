<template>
    <div id="buyer">
        <Page :results="results" :myRowClickHandler="onRowClick" :fields="fields" />
    </div>
</template>

<script>
import Page from "@/components/Page.vue";
import accountApi from "@/util/accountsApi";

export default {
    name: "buyer",
    props: {
        buyer: Object,
    },
    components: {
        Page,
    },

    data() {
        return {
            results: [],
            fields: [
                "id",
                { key: "datetime", sortable: true },
                "activityType",
                "details",
            ],
        };
    },
    methods: {
        getSearchResults() {
            accountApi.getActivities(this.buyer.id).then((response) => {
                this.results = response;
                this.totalResults = this.results.length;
            });
        },
        onRowClick() {},
    },
    watch: {
        account: {
            immediate: true,
            handler() {
                if (this.buyer.id) {
                    this.getSearchResults();
                }
            },
        },
    },
    mounted: function() {
        this.getSearchResults();
    },
};
</script>

<style scoped></style>
