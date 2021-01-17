<template>
    <div id="app">
         <div>
            <b-navbar toggleable="lg" type="dark" variant="info">
                <b-navbar-brand href="#"
                    >@kz-srikar</b-navbar-brand
                >
            </b-navbar>
        </div>
        <Home />
    </div>
</template>

<script>
import Home from "./views/Home";

export default {
    name: "App",
    components: {
        Home,
    },
    data() {
        return {};
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
</style>
