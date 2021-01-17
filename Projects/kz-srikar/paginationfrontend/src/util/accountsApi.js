import api from './apiExports';

const accountsApi = {
    getAllAccounts() {
        return fetch(`${api.serverAddress}accounts`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },

    getSearchAccounts(obj) {
        let reqParams = `page=${obj.currentPage}&page_size=${obj.pageSize}`;
        if(obj.q != "")
            reqParams = reqParams + `&name=${obj.q}`;
        if (obj.name != "")
            reqParams = reqParams + `&account_name=${obj.name}`;
        if (obj.ip_domain != "")
            reqParams = reqParams + `&ip_domain=${obj.ip_domain}`;
        if (obj.city != "")
            reqParams = reqParams + `&ip_geo_city=${obj.city}`;
        if (obj.state != "")
            reqParams = reqParams + `&ip_geo_state=${obj.state}`;
        if (obj.country != "")
            reqParams = reqParams + `&ip_geo_country=${obj.country}`;
        if (obj.type != "")
            reqParams = reqParams + `&type=${obj.type}`;
        if (obj.salesforce_id != "")
            reqParams = reqParams + `&sfdc_account_id=${obj.salesforce_id}`;

        console.log(reqParams);
        return fetch(`${api.serverAddress}accounts?${reqParams}`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },
};

export default accountsApi;