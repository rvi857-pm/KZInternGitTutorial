<template>
    <div id="app">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">@kz-srikar</b-navbar-brand>
        </b-navbar>
        <br />
        <v-breadcrumbs :items="items">
            <template v-slot:divider>
                <v-icon>mdi-chevron-right</v-icon>
            </template>
        </v-breadcrumbs>
        <router-view />
    </div>
</template>

<script>
import { mapState } from "vuex";

export default {
    name: "App",
    data() {
        return {};
    },
    computed: {
        ...mapState(["items"]),
    },
    methods: {},
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
</style>
