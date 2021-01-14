<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand >Sameer Dhiman</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>

             <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <b-input-group> 
                            <b-form-input v-model="input">Search</b-form-input>
                                <template #append>
                                    <b-dropdown :text="searchBy.toUpperCase()" variant="success">
                                         <b-dropdown-item @click="searchBy = 'any'">Any</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'id'">Id</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'name'">Name</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'doamin'">Domain</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'city'">City</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'state'">State</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'country'">Country</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'type'">Type</b-dropdown-item>
                                        <b-dropdown-item @click="searchBy = 'accountId'">Account Id</b-dropdown-item>
                                    </b-dropdown>
                                    <b-button variant="success" @click="search(searchBy + '=' + input)"> Search</b-button>
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
            input: ''
        }
    },
    props: [
        'search'
    ]
}
</script>