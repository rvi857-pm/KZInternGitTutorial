<template>
	<div>
		<b-navbar toggleable="lg" type="dark" variant="dark">
			<b-navbar-brand @click="update(0)" href="#">NavBar</b-navbar-brand>

			<b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

			<b-collapse id="nav-collapse" is-nav>
				<b-navbar-nav>
					<b-nav-item @click="update(1)">Accounts</b-nav-item>
					<b-nav-item @click="update(2)">Search</b-nav-item>
				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
	</div>
</template>

<script>
export default {
	name: "Header",
	props: {
		update: Function,
	},
};
</script>

<style></style>
