var app =angular.module("AccountsModule",[]);
	app.controller("AccountsController",function($scope,$http){
		$scope.accounts = [];
		$scope.accountsform={
			page:"",
			page_size:"",
			name:"",
			city:"",
			state:"",
			country:"",
			ipdomain:"",
			q:"",
			type:""
		};
		$scope.account={
			name:"",
			city:"",
			state:"",
			country:"",
			ip_domain:"",
			type:"",
			salesforce_id:""
		};
		
		$scope.fields=["name","city","country","state", "ip_domain", "type", "salesforce_id"];
		$scope.pagenumber="";
		$scope.url="http://localhost:8080/accounts?";
		$scope.accountview=false;
		$scope.querystring="";
		refreshAccountslist();
		function refreshAccountslist() {
					$scope.pagenumber="1";
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
			console.log($scope.url);	
   			$http({
                        method : 'GET',
                        url : $scope.url
                    }).then(function successCallback(response) {
						reseturl();
                        $scope.accounts = response.data;        
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
			if(!(($scope.accountsform).ipdomain==="")){
   				$scope.url= $scope.url + '&ip_domain=' + ($scope.accountsform).ipdomain;
				
   			}
			if(!(($scope.accountsform).q==="")){
   				$scope.url= $scope.url + '&q=' + ($scope.accountsform).q;
				
   			}
			if(!(($scope.accountsform).type==="")){
   				$scope.url= $scope.url + '&type=' + ($scope.accountsform).type;
				
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

		function resetobject(){
			$scope.accountsform={
				page:"",
				page_size:"",
				name:"",
				city:"",
				state:"",
				country:"",
				ipdomain:"",
				q:"",
				type:""
			}
		}
		
		$scope.ShowAccount= function(index){
			//console.log("check");
			($scope.account).name = ($scope.accounts[index]).name;
			($scope.account).city = ($scope.accounts[index]).city;
			($scope.account).state = ($scope.accounts[index]).state;
			($scope.account).country = ($scope.accounts[index]).country;
			($scope.account).type = ($scope.accounts[index]).type;
			($scope.account).salesforce_id = ($scope.accounts[index]).salesforce_id;
			($scope.account).ip_domain = ($scope.accounts[index]).ip_domain;
			
			$scope.accountview=true;
			console.log(name);
		};
   		
		$scope.ProcessString= function(){
			console.log("fields: " + $scope.fields);
			console.log("querystring: " + $scope.querystring);
			for(field of $scope.fields){
				console.log("Field: "+ field);
				var field_start = ($scope.querystring).indexOf(field);
				if(field_start!=-1){
					var field_removed = ($scope.querystring).slice(field_start + field.length + 2);
					var field_val = field_removed.slice(0,field_removed.indexOf('"'));
					console.log("field_Value: "+ field_val);
					($scope.accountsform)[field] = field_val;
					$scope.querystring = ($scope.querystring).replace(field + ':"' + field_val + '"', "");
					console.log("After Field removed: " + $scope.querystring);
				}
			 }
			 console.log("Query: " + ($scope.querystring).trim());
			 ($scope.accountsform).q = ($scope.querystring).trim();
			 console.log($scope.accountsform);
			
			$scope.AccountQuery();
			resetobject();
		}
		
   		
     });