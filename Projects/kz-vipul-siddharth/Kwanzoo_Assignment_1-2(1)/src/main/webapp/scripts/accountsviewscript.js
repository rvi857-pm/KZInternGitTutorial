var app =angular.module("AccountsModule",[]);
	app.controller("AccountsController",function($scope,$http){
		$scope.accounts = [];
		$scope.accountsform={
			page:"",
			page_size:"",
			name:"",
			city:"",
			state:"",
			country:""
		};
		$scope.pagenumber="";
		$scope.url="http://localhost:8080/accounts?";
		refreshAccountslist();
		function refreshAccountslist() {
					$scope.pagenumber="1";
					console.log($scope.pagenumber);
                    $http({
                        method : 'GET',
                        data: $scope.accountsform,
                        url : $scope.url 
                    }).then(function successCallback(response) {
                        $scope.accounts = response.data;
                        
                    }, function errorCallback(response) {
      
                        console.log(response.statusText);
                    });
   		};
   		$scope.AccountQuery = function(){
   			urlgenerator();
   			$http({
                        method : 'GET',
                        data: $scope.accountsform,
                        url : $scope.url
                    }).then(function successCallback(response) {
                        $scope.accounts = response.data;
                        reseturl();
                    }, function errorCallback(response) {
      
                        console.log(response.statusText);
                    });
   		}
   		
		$scope.setpagesize= function (value){
			($scope.accountsform).page_size=value;
			$scope.AccountQuery();
		};

   		function urlgenerator(){
   			if(!(($scope.accountsform).page==="")){
   				$scope.url= $scope.url + 'page=' + ($scope.accountsform).page;
   			}
   			if(!(($scope.accountsform).page_size==="")){
   				$scope.url= $scope.url + '&page_size=' + ($scope.accountsform).page_size;
   			}
   			if(!(($scope.accountsform).name==="")){
   				$scope.url= $scope.url + '&name=' + ($scope.accountsform).name;
   			}
   			if(!(($scope.accountsform).city==="")){
   				$scope.url= $scope.url + '&city=' + ($scope.accountsform).city;
   			}
   			if(!(($scope.accountsform).state==="")){
   				$scope.url= $scope.url + '&state=' + ($scope.accountsform).state;
   			}
   			if(!(($scope.accountsform).country==="")){
   				$scope.url= $scope.url + '&country=' + ($scope.accountsform).country;
   			}
   		};
		
		$scope.increamentpage=function(){
			console.log("success");
			$scope.pagenumber=(parseInt($scope.pagenumber)+1).toString();
			($scope.accountsform).page = $scope.pagenumber;
			$scope.AccountQuery();
		};
		
		$scope.decreamentpage=function(){
			$scope.pagenumber=(parseInt($scope.pagenumber)-1).toString();
			($scope.accountsform).page = $scope.pagenumber;
			$scope.AccountQuery();
		};
		
   		function reseturl(){
   			$scope.url="http://localhost:8080/accounts?";
   		}
   		
     });