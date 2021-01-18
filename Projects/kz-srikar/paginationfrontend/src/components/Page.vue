<template>
    <div class="overflow-auto">
        <b-table striped hover :items="results" :per-page="pageSize" small @row-clicked="myRowClickHandler">
        </b-table>
        <b-modal v-model="show" :title="rowAccountInfo.name">
            <p class="my-4">Ip Domain : {{rowAccountInfo.ipDomain}}</p>
            <p class="my-4">City : {{rowAccountInfo.city}}</p>
            <p class="my-4">State : {{rowAccountInfo.state}}</p>
            <p class="my-4">Country : {{rowAccountInfo.country}}</p>
            <p class="my-4">Salesforce Id : {{rowAccountInfo.salesforceId}}</p>
        </b-modal>
    </div>
</template>

<script>
export default {
    props: {
        pageSize: Number,
        results: Array,
    },
    data() {
        return {
            show: false,
            rowAccountInfo: {
            }
        };
    },
    methods: {
        myRowClickHandler(record) {
            this.show = true;
            this.rowAccountInfo.name = record.name
            this.rowAccountInfo = {
                name: record.name,
                ipDomain: record.ipDomain,
                city: record.city,
                state: record.state,
                country: record.country,
                type: record.type,
                salesforceId: record.salesforceId,
            }
        }
    }
};
</script>

<style scoped></style>
