<template>
    <div id="account">
        <h1 style="text-decoration: underline">
            {{ account.name }} - {{ account.score }}
        </h1>
        <p>Marketing Qualified : {{ account.marketing_qualified }}</p>
        <p>Total Buyers : {{ account.buyer_count }}</p>
        <br />
        <div class="Chart">
            <div>
                <h1 style="text-align:center;">Location Count</h1>
                <PieExample :info="locationInfo" />
            </div>
            <div>
                <h1 style="text-align:center;">Persona Count</h1>
                <PieExample :info="personaInfo" />
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
            :fields="fields"
            :myRowClickHandler="setBuyer"
        />
        <br />
    </div>
</template>

<script>
import PieExample from "@/util/PieExample";
import Page from "@/components/Page.vue";
import accountApi from "@/util/accountsApi";

export default {
    name: "account",
    props: {
        account: Object,
        setBuyer: Function,
    },
    components: {
        Page,
        PieExample,
    },
    data() {
        return {
            fields: [
                "id",
                "city",
                "state",
                "country",
                "source",
                "job_level",
                "job_function",
            ],
            totalResults: 0,
            results: [],
            currentPage: 1,
            pageSize: 10,
            locationInfo: {},
            personaInfo: {},
            activityInfo: {},
        };
    },
    methods: {
        getPageSize() {
            if (this.pageSize == "") return 0;
            return parseInt(this.pageSize);
        },
        getSearchResults(p) {
            let obj = {
                currentPage: p,
                pageSize: this.pageSize,
                accountId: this.account.id,
            };
            accountApi.getBuyers(obj).then((response) => {
                this.totalResults = response.totalElements;
                this.results = response.content;
            });
        },
        getLocationCountData() {
            let labels = [];
            let data = [];
            let colors = [];
            for (let i = 0; i < this.account.location_count.length; i++) {
                let location = this.account.location_count[i];
                let n = (Math.random() * 0xfffff * 1000000).toString(16);
                colors.push("#" + n.slice(0, 6));
                labels.push(
                    location.city +
                        ", " +
                        location.state +
                        ", " +
                        location.country
                );
                data.push(location.count);
            }
            this.locationInfo = {
                labels,
                data,
                colors,
            };
        },
        getPersonaCountData() {
            let labels = [];
            let data = [];
            let colors = [];
            for (let i = 0; i < this.account.persona_count.length; i++) {
                let persona = this.account.persona_count[i];
                let n = (Math.random() * 0xfffff * 1000000).toString(16);
                colors.push("#" + n.slice(0, 6));
                labels.push(persona.job_level + ", " + persona.job_function);
                data.push(persona.count);
            }
            this.personaInfo = {
                labels,
                data,
                colors,
            };
        },
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
            let activityCount = this.account.activity_count;
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
                this.getSearchResults(1);
                if (this.account.id) {
                    this.getLocationCountData();
                    this.getPersonaCountData();
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
    },
};
</script>

<style>
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
