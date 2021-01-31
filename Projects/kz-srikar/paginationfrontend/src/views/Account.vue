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
                "jobLevel",
                "jobFunction",
            ],
            results: [],
            locationInfo: {},
            personaInfo: {},
            activityInfo: {},
        };
    },
    methods: {
        getSearchResults() {
            accountApi.getBuyers(this.account.id).then((response) => {
                this.results = response;
                this.totalResults = this.results.length;
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
                this.getSearchResults();
                if (this.account.id) {
                    this.getLocationCountData();
                    this.getPersonaCountData();
                    this.getActivityCountData();
                }
            },
        },
    },
    mounted: function() {
        this.getSearchResults();
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
