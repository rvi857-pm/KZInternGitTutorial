<template>
    <div id="buyer">
        <br />
        <div class="Chart">
            <div>
                <h1 style="text-decoration: underline">
                    Score - {{ buyer.score }}
                </h1>
                <p>Buyer Id : {{ buyer.id }}</p>
                <p>
                    Location : {{ buyer.city }} , {{ buyer.state }} ,
                    {{ buyer.country }}
                </p>
                <p>Source : {{ buyer.source }}</p>
                <p>Job Level : {{ buyer.job_level }}</p>
                <p>Job Function : {{ buyer.job_function }}</p>
                <p>Marketing Qualified : {{ buyer.marketing_qualified }}</p>
                <p>Total Activities : {{ totalResults }}</p>
            </div>
            <div>
                <h1 style="text-align:center;">Activity Count</h1>
                <PieExample :info="activityInfo" />
            </div>
        </div>

        <br />
        <div class="Pagination">
            <b-col sm="1">
                <b-form-input
                    v-model="pageSize"
                    min="1"
                    type="number"
                ></b-form-input>
            </b-col>
            <b-pagination
                v-model="currentPage"
                :total-rows="totalResults"
                :per-page="pageSize"
                aria-controls="my-table"
                align="center"
            ></b-pagination>
        </div>
        <Page
            :results="results"
            :myRowClickHandler="onRowClick"
            :fields="fields"
        />
    </div>
</template>

<script>
import PieExample from "@/util/PieExample";
import Page from "@/components/Page.vue";
import accountApi from "@/util/accountsApi";
import { mapState, mapActions } from "vuex";

export default {
    name: "buyer",
    components: {
        Page,
        PieExample,
    },

    data() {
        return {
            fields: [
                "id",
                { key: "datetime", sortable: true },
                "activityType",
                "details",
            ],
            totalResults: 0,
            results: [],
            currentPage: 1,
            pageSize: 10,
            activityInfo: {},
        };
    },
    computed: {
        ...mapState(["buyer", "account"]),
    },
    methods: {
        ...mapActions(["setItems"]),
        getPageSize() {
            if (this.pageSize == "") return 0;
            return parseInt(this.pageSize);
        },
        getSearchResults(p) {
            let obj = {
                currentPage: p,
                pageSize: this.pageSize,
                buyerId: this.buyer.id,
            };
            accountApi.getActivities(obj).then((response) => {
                this.totalResults = response.totalElements;
                this.results = response.content;
            });
        },
        onRowClick() {},
        getActivityCountData() {
            let labels = [
                "ad_clicks",
                "form_fills",
                "live_chats",
                "website_visits",
            ];
            let colors = [];
            for (let i = 0; i < 4; i++) {
                let n = (Math.random() * 0xfffff * 1000000).toString(16);
                colors.push("#" + n.slice(0, 6));
            }
            let activityCount = this.buyer.activity_count;
            let data = [
                activityCount.ad_clicks,
                activityCount.form_fills,
                activityCount.live_chats,
                activityCount.website_visits,
            ];
            this.activityInfo = {
                labels,
                data,
                colors,
            };
        },
    },
    watch: {
        account: {
            immediate: true,
            handler() {
                if (this.buyer.id) {
                    this.getSearchResults(1);
                    this.getActivityCountData();
                }
            },
        },
        currentPage: function(newVal, oldVal) {
            if (newVal == 0) {
                this.currentPage = 1;
                return;
            }
            if (oldVal == 0) return;
            this.getSearchResults(this.currentPage);
        },
        pageSize: function() {
            this.getSearchResults(this.currentPage);
        },
    },

    mounted: function() {
        this.getSearchResults(1);
        let items = [
            {
                text: "All Accounts",
                disabled: false,
                to: "/",
            },
            {
                text: this.account.name,
                disabled: false,
                to: `/${this.account.name}`,
                exact: true,
            },
            {
                text: this.buyer.id,
                disabled: true,
                to: `/${this.account.name}/${this.buyer.id}`,
            },
        ];
        this.setItems(items);
    },
};
</script>

<style scoped>
.Chart {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}
.Pagination {
    display: flex;
    flex-direction: row;
    justify-content: center;
}
</style>
