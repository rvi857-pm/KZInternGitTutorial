<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label id="l"
        >Add accounts
        <input
          type="file"
          id="file"
          ref="file"
          v-on:change="handleFileUpload()"
        />
      </label>
      <br />
      <b-button variant="success" v-on:click="submitFile()">Submit</b-button>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      file: "",
    };
  },
  methods: {
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
    submitFile() {
      let formData = new FormData();
      formData.append("file", this.file);

      axios
        .post("http://localhost:8080/accounts", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(function () {
          alert(" Uploaded successfully");
          console.log("Success");
        })
        .catch(function () {
          alert(" Error ");
          console.log("failure");
        });
    },
  },
};
</script>

<style>
#l {
  display: flex;
  justify-content: left;
  align-items: center;
  color: Black;
  font-weight: bold;
}
</style>