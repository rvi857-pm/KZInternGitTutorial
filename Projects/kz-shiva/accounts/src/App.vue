<template>
	<div id="app">
		<Header />
		<template>
			<b-breadcrumb :items="items"></b-breadcrumb>
		</template>
		<router-view />
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
import Header from "./components/Header";

export default {
	name: "App",

	components: {
		Header,
	},

	computed:{
		...mapGetters({
			items: 'getItems'
		})
	}
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
</style>
