app.service('LoadDataService',function($http){
    this.buyers = [];
    this.loadbuyerdata = function (account_id, accountsform) {
        $http({
            method: 'GET',
            data: accountsform,
            url: "http://localhost:8080/buyers?id=" + account_id,
        }).then(function successCallback(response) {
            console.log(response);
            return new Promise((resolve, reject) => {
                resolve(response.data);
            }
            )

        }, function errorCallback(response) {

            console.log(response.statusText);
            return null;
        });
    }
})