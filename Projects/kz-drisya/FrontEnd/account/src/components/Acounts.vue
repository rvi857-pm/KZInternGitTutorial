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
    <div class="overflow-auto">
      <b-pagination
        v-model="account.page_no"
        :per-page="account.page_size"
        :total-rows="1400"
      ></b-pagination>
      <b-table
        :items="friends"
        :per-page="account.page_size"
        :current-page="account.page_no"
      ></b-table>
    </div>
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
      let requestParam = `?pageno=${this.account.page_no}&page_size=${this.page_size}`;
      if (this.account.account_name !== "")
        requestParam += `&name=${this.account.account_name}`;
      if (this.account.ip_geo_city !== "")
        requestParam += `&city=${this.account.ip_geo_city}`;
      if (this.account.ip_geo_state !== "")
        requestParam += `&state=${this.account.ip_geo_state}`;
      if (this.account.ip_geo_country !== "")
        requestParam += `&country=${this.account.ip_geo_country}`;

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