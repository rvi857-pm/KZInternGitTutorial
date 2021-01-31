<template>
    <div>
        <div class="container">
            <form @submit.prevent="onSubmit">
                <div class="form-group">
                    <input type="file" @change="onFileUpload" accept=".csv" />
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-block btn-lg">
                        Upload File
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>

import {post} from 'axios';

export default {
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
            const url = "http://localhost:8080/upload-accounts-csv";
            const formData = new FormData();
            formData.append("file", this.FILE);
            const config = {
                headers: {
                    "content-type": "multipart/form-data",
                },
            };
            post(url, formData, config);
        },
    },
};
</script>

<style scoped>
.container {
    max-width: 600px;
}
</style>
