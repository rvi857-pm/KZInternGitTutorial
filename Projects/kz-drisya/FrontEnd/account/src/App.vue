<template>
  <div>
    <NavBar />
    <h1 id="img">Welcome to Accounts!!</h1>
    <Account />
  </div>
</template>
<script>
import NavBar from "./components/NavBar";
import Account from "./components/Acounts";
export default {
  name: "App",
  components: {
    NavBar,
    Account,
  },
};
</script>

<style>
#input {
  width: 19%;
  height: 30%;
}
#label {
  padding-left: 1%;
  font-size: 150%;
  color: green;
}
#img {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  color: Black;
  font-weight: bold;
}
</style>
