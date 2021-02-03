<template>
  <div>
    <NavBar />
    <br />
    <router-view />
  </div>
</template>
<script>
import NavBar from "./components/NavBar";
export default {
  name: "App",
  components: {
    NavBar,
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
