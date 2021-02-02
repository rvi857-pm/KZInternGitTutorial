/**
 * This method forms a url based on the present state
 * @param
 * @return url for the api call
 */
const getAccountUrl = (state) => {
	let server = "http://localhost:8080/accounts?metrics=all";
	let url = "";

	url += state.search
		? "&search"+ state.search
		: "";

	url += state.name
		? "&name=" + state.name
		: "";

	url += state.ipDomain
		? "&ip_domain=" + state.ipDomain
		: "";

	url += state.city
		? "&city=" + state.city
		: "";

	url += state.state
		? "&state=" + state.state
		: "";

	url += state.country
		? "&country=" + state.country
		: "";

	url += state.type
		? "&type=" + state.type
		: "";

	url += state.salesforceId
		? "&salesforce_id=" + state.salesforceId
		: "";

	url += state.page
		? "&page=" + (state.page - 1)
		: "";

	url += state.pageSize
		? "&page_size=" + state.pageSize
		: "";

	return server + url;
};

export default{
	getAccountUrl
};
