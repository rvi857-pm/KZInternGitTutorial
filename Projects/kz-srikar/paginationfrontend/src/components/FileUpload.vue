<template>
    <div>
        <div class="container">
            <form @submit.prevent="onSubmit">
                <div class="form-group">
                    <input type="file" @change="onFileUpload" accept=".csv" />
                    <button class="btn btn-primary btn-sm">
                        Upload Account File
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import { post } from "axios";

export default {
    props: {
        refresh: Function,
    },
    data() {
        return {
            FILE: null,
        };
    },
    methods: {
        onFileUpload(event) {
            this.FILE = event.target.files[0];
        },
        onSubmit() {
            if (this.FILE == null) {
                return;
            }
            const url = "http://localhost:8080/upload-accounts-csv";
            const formData = new FormData();
            formData.append("file", this.FILE);
            const config = {
                headers: {
                    "content-type": "multipart/form-data",
                },
            };
            post(url, formData, config).then(() => {
                this.refresh();
                alert("file uploaded");
            });
        },
    },
};
</script>

<style scoped>

</style>
