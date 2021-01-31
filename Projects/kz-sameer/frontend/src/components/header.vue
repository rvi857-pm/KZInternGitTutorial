<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand >Sameer Dhiman</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" v-if="load" is-nav>

             <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <b-input-group> 
                            <b-form-input v-model="input">Search</b-form-input>
                                <template #append>
                                    <b-dropdown :text="searchBy.toUpperCase()" variant="success">
                                         <b-dropdown-item v-for="(searchItem, index) in searchList"  @click='searchBy = searchItem' :key="index" :value="searchItem">{{ searchItem.toUpperCase() }}</b-dropdown-item>
                                    </b-dropdown>
                                    <b-button variant="success" @click="searchParser(input)"> Search</b-button>
                                </template>
                        </b-input-group>
                    </b-nav-form>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>
<script>
export default {
    data(){
        return{
            searchBy: 'any',
            input: '',
            searchList: ["any", "id", "ip Domain", "city", "state", "country", "type", "salesforce Id"],
        }
    },
    props: [
        'search',
        'load'
    ],
    methods: {

        searchParser(str){
            if(this.searchBy === 'any'){
                let parsedStr = ""
                for (let k=0; k < 8; k++){
                    let indexOfFilter = str.indexOf(this.searchList[k] + ":")
                    if(indexOfFilter > -1){
                        indexOfFilter = indexOfFilter + this.searchList[k].length + 2
                    }
                    else{
                        continue
                    }
                    let filter =str.slice(indexOfFilter, str.indexOf('"', indexOfFilter)) 
                    str = str.replace(filter, "").replace(this.searchList[k] + ':""', "")
                    parsedStr = parsedStr + this.searchList[k] + "=" + filter + "&"
                }
                str = str.trim()
                if(str !== ''){
                    parsedStr = "any=" + str + "&" + parsedStr
                }
                this.search(parsedStr)
            }
            else{
                this.search(this.searchBy.replace(" ", "") + "=" + str)
            }
        }
    }
}
</script>