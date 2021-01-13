<template>
  <div>
    <ul>
      <li class="list-group-item" v-for="friend in friends" v-bind:key="friend">
        {{ friend.id }}
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data() {
    return {
      friends: [],
    };
  },
  mounted: function () {
    console.log("mounted");
    fetch(`http://localhost:8080/accounts?page=1`, {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((jsonResponse) => {
        this.friends = jsonResponse;
      });
    // console.log(this.friends);
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