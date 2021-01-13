<template>
  <div>
    <!--<ul>
      <table>
        <tr
          class="list-group-item"
          v-for="friend in friends"
          v-bind:key="friend.id"
        >
          <td headers="dl">{{ friend.id }}</td>
          <td>{{ friend.account_name }}</td>
          <td>{{ friend.ip_Domain }}</td>
          <td>{{ friend.ip_Geo_City }}</td>
          <td>{{ friend.ip_Geo_State }}</td>
          <td>{{ friend.ip_Geo_Country }}</td>
          <td>{{ friend.sfdc_Account_ID }}</td>
        </tr>
      </table>
    </ul>-->
    <b-table striped :items="friends"></b-table>
  </div>
</template>
<script>
export default {
  props: {
    account: Object,
  },
  data() {
    return {
      friends: [],
    };
  },
  methods: {
    getRequest() {
      let requestParam = `?` + `name=${this.account.account_name}`;
      if (this.account.account_name === "") requestParam = "";
      console.log(this.account);
      fetch(`http://localhost:8080/accounts${requestParam}`, {
        method: "get",
      })
        .then((response) => {
          return response.json();
        })
        .then((jsonResponse) => {
          this.friends = jsonResponse;
        });
    },
  },
  watch: {
    account: {
      handler() {
        this.getRequest();
      },
      deep: true,
      immediate: true,
    },
  },
  mounted: function () {
    fetch(`http://localhost:8080/accounts`, {
      method: "get",
    })
      .then((response) => {
        return response.json();
      })
      .then((jsonResponse) => {
        this.friends = jsonResponse;
      });
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