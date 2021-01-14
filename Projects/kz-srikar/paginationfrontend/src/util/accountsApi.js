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

    getSpecificSearchAccounts(obj) {
        let reqParams = `page=${obj.currentPage}&page_size=${obj.pageSize}`;
        if (obj.accountName != "")
            reqParams = reqParams + `&account_name=${obj.accountName}`;
        if (obj.ipDomain != "")
            reqParams = reqParams + `&ip_domain=${obj.ipDomain}`;
        if (obj.ipGeoCity != "")
            reqParams = reqParams + `&ip_geo_city=${obj.ipGeoCity}`;
        if (obj.ipGeoState != "")
            reqParams = reqParams + `&ip_geo_state=${obj.ipGeoState}`;
        if (obj.ipGeoCountry != "")
            reqParams = reqParams + `&ip_geo_country=${obj.ipGeoCountry}`;
        if (obj.type != "")
            reqParams = reqParams + `&type=${obj.type}`;
        if (obj.sfdcAccountId != "")
            reqParams = reqParams + `&sfdc_account_id=${obj.sfdcAccountId}`;

        return fetch(`${api.serverAddress}accounts?${reqParams}`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },

    getAllSearchAccounts(obj) {
        let reqParams = `page=${obj.currentPage}&page_size=${obj.pageSize}&name=${obj.name}`;
        return fetch(`${api.serverAddress}accounts?${reqParams}`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    }
};

export default accountsApi;