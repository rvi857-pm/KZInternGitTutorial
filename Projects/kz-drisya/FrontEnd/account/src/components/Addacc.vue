<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label
        >Add accounts here(file format csv)
        <input
          type="file"
          id="file"
          ref="file"
          v-on:change="handleFileUpload()"
        />
      </label>
      <button v-on:click="submitFile()">Submit</button>
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
          console.log("Success");
        })
        .catch(function () {
          console.log("failure");
        });
    },
  },
};
</script>

<style>
</style>