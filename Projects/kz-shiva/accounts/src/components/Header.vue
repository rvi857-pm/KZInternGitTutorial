<template>
  <div>
    <b-nav tabs>
      <b-navbar-brand
				disabled
				@click="update(0)"
			><h3>Kwanzoo</h3></b-navbar-brand>
      <b-nav-item
				@click="update(1)"
			><h4>Accounts</h4></b-nav-item>
      <b-nav-item
				@click="update(2)"
			><h4>Search</h4></b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
	name: "Header",
	props: {
		update: Function
	}
}
</script>

<style>

</style>