<template>
<div>

    <b-container>

    <b-input-group> 
        <b-form-file v-model="file" :state="Boolean(file)" placeholder="Choose a file or drop it here..." drop-placeholder="Drop file here..."></b-form-file>
        <template #append>
            <b-button variant="success" v-b-popover.hover.top="res" @click="submitFile()">Upload</b-button>
        </template>
    </b-input-group>

    </b-container>


</div>
</template>

<script>
import axios from 'axios'

  export default {
    data(){
      return {
        file: null,
        res: ""
      }
    },

    methods: {
        test(response){
            this.$bvToast.toast(`${response}`, {
                title: 'File Upload',
                autoHideDelay: 5000
            })
        },
        submitFile(){

            let formData = new FormData();
            formData.append('file', this.file)

            axios.post( 'http://localhost:8080/file',
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            ).then((response) => {
                this.test(response.data)

            })
      }
    }
  }
</script>