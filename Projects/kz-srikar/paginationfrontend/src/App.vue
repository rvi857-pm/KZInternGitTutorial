<template>
    <div id="app">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">@kz-srikar</b-navbar-brand>
        </b-navbar>
        <br />

        <div v-if="isHome === true">
            <div id="breadCrumb">
                <p>All Accounts</p>
            </div>
            <Home :setAccount="setAccount" />
        </div>
        <div v-else-if="isAccount === true">
            <div id="breadCrumb">
                <p>
                    <a @click="goToHome">All Accounts</a>
                    / {{ account.name }}
                </p>
            </div>
            <Account :account="account" :setBuyer="setBuyer" />
        </div>
        <div v-else-if="isBuyer === true">
            <div id="breadCrumb">
                <p>
                    <a @click="goToHome">All Accounts</a>
                    / <a @click="goToAccount">{{ account.name }}</a> /
                    {{ buyer.id }}
                </p>
            </div>
            <Buyer :buyer="buyer" />
        </div>
    </div>
</template>

<script>
import Home from "./views/Home";
import Account from "./views/Account";
import Buyer from "./views/Buyer";

export default {
    name: "App",
    components: {
        Home,
        Account,
        Buyer,
    },
    data() {
        return {
            isHome: true,
            isAccount: false,
            isBuyer: false,
            account: {},
            buyer: {},
        };
    },
    methods: {
        setAccount(account) {
            this.account = account;
            this.isHome = false;
            this.isAccount = true;
        },
        setBuyer(buyer) {
            this.buyer = buyer;
            this.isAccount = false;
            this.isBuyer = true;
        },
        goToHome() {
            this.isHome = true;
            this.isAccount = false;
            this.isBuyer = false;
        },
        goToAccount() {
            this.isHome = false;
            this.isAccount = true;
            this.isBuyer = false;
        },
    },
};
</script>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}
#breadCrumb {
    display: flex;
    align-items: flex-start;
    padding-left: 50px;
}
a {
    color: green;
    cursor: pointer;
}
p {
    font-weight: bold;
}
</style>
