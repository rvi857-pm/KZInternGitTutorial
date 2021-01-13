<template>
  <div id="app">
    {{ msg }}
    <Account />
  </div>
</template>

<script>
import Account from "./components/Acounts";
export default {
  name: "App",
  components: {
    Account,
  },
  data() {
    return {
      msg: "hey",
    };
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
  margin-top: 60px;
}
</style>
