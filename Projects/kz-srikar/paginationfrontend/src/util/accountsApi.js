import api from './apiExports';

const accountsApi = {
    getActivities(obj) {
        return fetch(`${api.serverAddress}activities?id=${obj.buyerId}&page=${obj.currentPage}&pageSize=${obj.pageSize}`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },

    getBuyers(obj) {
        return fetch(`${api.serverAddress}buyers?id=${obj.accountId}&page=${obj.currentPage}&pageSize=${obj.pageSize}&metrics=all`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },

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
        let reqParams = `page=${obj.currentPage}&pageSize=${obj.pageSize}`;
        if(obj.q != "")
            reqParams = reqParams + `&q=${obj.q}`;
        if (obj.name != "")
            reqParams = reqParams + `&name=${obj.name}`;
        if (obj.ip_domain != "")
            reqParams = reqParams + `&ipDomain=${obj.ip_domain}`;
        if (obj.city != "")
            reqParams = reqParams + `&city=${obj.city}`;
        if (obj.state != "")
            reqParams = reqParams + `&state=${obj.state}`;
        if (obj.country != "")
            reqParams = reqParams + `&country=${obj.country}`;
        if (obj.type != "")
            reqParams = reqParams + `&type=${obj.type}`;
        if (obj.salesforce_id != "")
            reqParams = reqParams + `&salesforceId=${obj.salesforce_id}`;

        return fetch(`${api.serverAddress}accounts?${reqParams}&metrics=all`, {
            method: 'get',
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            return jsonResponse;
        }).catch(error => console.log(error));
    },
};

export default accountsApi;