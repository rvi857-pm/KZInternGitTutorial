<template>
    <div id="home">
        <FileUpload :refresh="refresh" />
        <div class="searchBox">
            <b-form-input
                class="p-10"
                v-model="searchText"
                placeholder="Enter search pattern"
            ></b-form-input>
            <b-button @click="onClickAllSubmit" size="sm">Search</b-button>
        </div>
        <p class="p-2">
            Query Example ->
            <mark>
                name:"xyz" ip_domain:"xyz" city:"xyz" state:"xyz" country:"xyz"
                type:"xyz" salesforce_id:"xyz" xyz
            </mark>
        </p>
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
            :pageSize="getPageSize()"
            :results="results"
            :fields="fields"
            :myRowClickHandler="onClickAccount"
        />
        <br />
    </div>
</template>

<script>
import Page from "@/components/Page.vue";
import FileUpload from "@/components/FileUpload.vue";
import accountApi from "@/util/accountsApi";
import { mapActions } from "vuex";

export default {
    name: "home",
    components: {
        Page,
        FileUpload,
    },
    data() {
        return {
            fields: [
                "id",
                "name",
                "ip_domain",
                "city",
                "state",
                "country",
                "type",
                "salesforce_id",
            ],
            searchText: "",
            totalResults: 0,
            results: [],
            currentPage: 1,
            pageSize: 10,
            searchParam: {
                q: "",
                name: "",
                ip_domain: "",
                city: "",
                state: "",
                country: "",
                type: "",
                salesforce_id: "",
            },
        };
    },
    methods: {
        ...mapActions(["setAccount", "setItems"]),
        onClickAccount(account) {
            this.setAccount(account);
            this.$router.push(`/${account.name}`);
        },
        parseSearchText() {
            this.searchParam = {
                q: "",
                name: "",
                ip_domain: "",
                city: "",
                state: "",
                country: "",
                type: "",
                salesforce_id: "",
            };
            const parseText = this.searchText;
            let presentKey = "";
            let presentValue = "";
            let inValue = false;
            let inKey = true;
            for (let i = 0; i < parseText.length; i++) {
                if (parseText[i] == ":") {
                    inKey = false;
                    continue;
                }
                if (parseText[i] == '"') {
                    inValue = !inValue;
                    continue;
                }
                if (inKey && parseText[i] != " ") presentKey += parseText[i];
                if (parseText[i] == " ") {
                    if (!inValue && presentValue != "") {
                        if (this.searchParam[presentKey] == "")
                            this.searchParam[presentKey] = presentValue;
                        presentKey = "";
                        presentValue = "";
                        inKey = true;
                        continue;
                    }
                }
                if (inValue) presentValue += parseText[i];
            }
            if (presentValue == "") this.searchParam.q = presentKey;
            else this.searchParam[presentKey] = presentValue;
        },
        getPageSize() {
            if (this.pageSize == "") return 0;
            return parseInt(this.pageSize);
        },
        getSearchResults(p) {
            let obj = {
                currentPage: p,
                pageSize: this.pageSize,
                ...this.searchParam,
            };
            accountApi.getSearchAccounts(obj).then((response) => {
                this.totalResults = response.totalElements;
                this.results = response.content;
            });
        },
        onClickAllSubmit() {
            this.parseSearchText();
            this.getSearchResults(1);
            this.currentPage = 0;
        },
        refresh() {
            this.getSearchResults(this.currentPage);
        },
    },
    watch: {
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
        ];
        this.setItems(items);
    },
};
</script>

<style>
.btn {
    padding: 20px;
    margin: 20px;
}
.searchBox {
    display: flex;
    flex-direction: row;
    margin-right: 100px;
    margin-left: 100px;
    align-items: center;
}
.Pagination {
    display: flex;
    flex-direction: row;
    justify-content: center;
}
</style>
