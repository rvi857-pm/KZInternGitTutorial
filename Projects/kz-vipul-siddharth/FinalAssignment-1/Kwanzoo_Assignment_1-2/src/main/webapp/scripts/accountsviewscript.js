var app =angular.module("AccountsModule",[]);
	app.controller("AccountsController",function($scope,$http,UtilityService){
		$scope.accounts = [];
		$scope.buyers=[];
		$scope.activities=[];
		$scope.breadcrumblist=[];
		$scope.object={};
		$scope.Template = {
			list:[],
			clickfn:null,
			tableheader:[],
			object:null
		}
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
			id:"",
			name:"",
			city:"",
			state:"",
			country:"",
			ip_domain:"",
			type:"",
			salesforce_id:"",
			score:0,
			market_qualified:false
		};
		
		$scope.buyer={
			id: "",
			name: "",
			account_id: "",
			job_level: "",
			job_function: "",
			city: "",
			state: "",
			country: "",
			source: ""
		}

		$scope.activity={
			date:"",
			type:"",
			details:""
		}
		$scope.buyertableheader = ["job_level","job_function","city","state","country", "source"];
		$scope.activitytableheader=["date", "type", "details"];
		$scope.fields=["name","city","country","state", "ip_domain", "type", "salesforce_id"];
		$scope.pagenumber="";
		$scope.url="http://localhost:8080/accounts?";
		$scope.detailedview=false;
		$scope.tableview=true;
		$scope.accountview=false;
		$scope.accountstableview=true;
		$scope.buyerview = false;
		$scope.activityview=false;
		$scope.querystring="";
		$scope.currentview = "Accounts Table"
		$scope.lastpage=false;
		$scope.locationlist=[];
		$scope.personalist=[];
		$scope.activitylist=[];
		refreshAccountslist();
		function refreshAccountslist() {
					$scope.pagenumber="1";
                    $http({
                        method : 'GET',
                        data: $scope.accountsform,
                        url : $scope.url 
                    }).then(function successCallback(response) {
						
						$scope.accounts = response.data;
						loadtmpldata($scope.fields,{},$scope.accounts,$scope.ShowAccount);
						
                    }, function errorCallback(response) {
      
                        console.log(response.statusText);
                    });
   		};
   		$scope.AccountQuery = function(){
   			$scope.url = UtilityService.urlgenerator($scope.accountsform,$scope.url);
			//console.log($scope.url);
   			$http({
                        method : 'GET',
                        url : $scope.url
                    }).then(function successCallback(response) {
						reseturl();
						$scope.accounts = response.data;   
						loadtmpldata($scope.fields,{},$scope.accounts,$scope.ShowAccount);
						if(($scope.accountsform).page==="1" | ($scope.accountsform).page===""){
							$scope.pagenumber = "1";
							document.getElementById("PreviousButton").classList.add("disabled");
						}
						else{
							$scope.pagenumber = ($scope.accountsform).page;
							document.getElementById("PreviousButton").classList.remove("disabled");	                        
						}
						if($scope.accounts.length === 0){
							//console.log("success");
							document.getElementById("NextButton").classList.add("disabled");
							$scope.lastpage=true;
						}
						else{
							document.getElementById("NextButton").classList.remove("disabled");
							$scope.lastpage = false;
						}
                    }, function errorCallback(response) {
						reseturl();
                        console.log(response.statusText);
                    });
   		}
   		
		$scope.setpagesize= function (value){
			($scope.accountsform).page_size=value;
			$scope.AccountQuery();
		};
		
		$scope.increamentpage=function(){
			if(!(document.getElementById("NextButton").classList.contains("disabled"))){
				$scope.pagenumber=(parseInt($scope.pagenumber)+1).toString();
				($scope.accountsform).page = $scope.pagenumber;
				$scope.AccountQuery();
			}
		};
		
		$scope.decreamentpage=function(){
			if(!(document.getElementById("PreviousButton").classList.contains("disabled"))){
				$scope.pagenumber=(parseInt($scope.pagenumber)-1).toString();
				($scope.accountsform).page = $scope.pagenumber;
				$scope.AccountQuery();
			}
		};
		
   		function reseturl(){
   			$scope.url="http://localhost:8080/accounts?";
   		}
		
		$scope.ShowAccount= function(index){
			$scope.account = $scope.accounts[index];
			loadaccountdata(($scope.account).id);
			loadbuyerdata(($scope.account).id,$scope.accountsform);
		};

		$scope.ShowBuyer = function(index){
			$scope.buyer = $scope.buyers[index];
			loadactivitydata(($scope.buyer).id);
		}

		$scope.ShowActivity = function(index){
			console.log("check");
			$scope.activity = ($scope.activities)[index];
			// loadtmpldata([],$scope.activity,[],null);
			setview("Activity",true);
		}

   		
		$scope.ProcessString= function(){
			var tempstring = $scope.querystring;
			if(tempstring.length == 0){
				$scope.accountsform = UtilityService.resetobject();
				$scope.AccountQuery();
			}
			else{
				$scope.accountsform = UtilityService.resetobject();
				$scope.accountsform = UtilityService.extractobjectfromquery($scope.querystring,$scope.fields);
				$scope.AccountQuery();
			}
		}  		
	
		function processlocationlist(acclocationlist){
			($scope.locationlist) = [['Location', 'Number of Buyers']];
			for(locationobj of acclocationlist){
				var locationsample = Array.from([locationobj.city + "," +locationobj.state + ","+ locationobj.country, locationobj.count]);
				($scope.locationlist).push(locationsample);
			}
			//console.log($scope.locationlist);
		}

		function processpersonalist(accpersonalist){
			($scope.personalist) = [['Persona', 'Number of Buyers']];
			for(personaobj of accpersonalist){
				var personasample = Array.from([personaobj.job_level + "," +personaobj.job_function , personaobj.count]);
				($scope.personalist).push(personasample);
			}
			//console.log($scope.locationlist);
		}

		function processactivitylist(accactivitylist){
			($scope.activitylist) = [['Activity', 'Number of Buyers']];
			for(var activity_type in accactivitylist){
				var activitysample = Array.from([activity_type,accactivitylist[activity_type]]);
				($scope.activitylist).push(activitysample);
			}
			//console.log($scope.locationlist);
		}

		function loadaccountdata(account_id){
			accactivitylist = [];
			if(!(account_id==="")){
				$http({
					method : 'GET',
					data: $scope.accountsform,
					url : "http://localhost:8080/accounts?metric=all&id=" + account_id,
				}).then(function successCallback(response) {
					
					var temp = response.data;
					accactivitylist = (temp[0]).activity_count;
					acclocationlist = (temp[0]).Location_count;
					accpersonalist = (temp[0]).Persona_count;
					// console.log(accactivitylist);
					($scope.account).score = (temp[0]).score;
					($scope.account).market_qualified = (temp[0]).market_qualified;
					processlocationlist(acclocationlist);
					processpersonalist(accpersonalist);
					processactivitylist(accactivitylist);
					UtilityService.createchart($scope.activitylist,'activity_piechart');
					UtilityService.createchart($scope.locationlist,'location_piechart');
					UtilityService.createchart($scope.personalist,'persona_piechart');
				}, function errorCallback(response) {

					console.log(response.statusText);
				});
			}
		}

		function loadbuyerdata(account_id){
			$http({
				method : 'GET',
				data: $scope.accountsform,
				url : "http://localhost:8080/buyers?id=" + account_id,
			}).then(function successCallback(response) {
					
				$scope.buyers = response.data;
				setview("Account",true)
				// console.log($scope.buyers);
			}, function errorCallback(response) {

				console.log(response.statusText);
			});
		}

		function loadactivitydata(buyer_id){
			$http({
				method : 'GET',
				data: $scope.accountsform,
				url : "http://localhost:8080/activities?buyer_id=" + buyer_id,
			}).then(function successCallback(response) {
					
				$scope.activities = response.data;
				// loadtmpldata($scope.activitytableheader,$scope.buyer,$scope.activities,$scope.ShowActivity);
				setview("Buyer",true);
				// console.log($scope.buyers);
			}, function errorCallback(response) {

				console.log(response.statusText);
			});
		}

		function loadtmpldata(tableheader,object,list,clickfn){
			($scope.Template).tableheader = tableheader;
			($scope.Template).object = object;
			($scope.Template).list = list;
			($scope.Template).clickfn = clickfn;

		}

		
		$scope.Navigateto = function(view){
			while(!(($scope.breadcrumblist).pop() === view));
			setview(view, false);
			// console.log(($scope.breadcrumblist).pop());
		}

		function setview(view, addcurretview){
			$scope.accountstableview =false;
			$scope.accountview =false;
			$scope.buyerview=false;
			$scope.activityview = false;
			if(view === "Accounts Table"){
				$scope.accountstableview =true;
				loadtmpldata($scope.fields,{},$scope.accounts,$scope.ShowAccount);
			}
			if(view === "Account"){
				$scope.accountview = true;
				loadtmpldata($scope.buyertableheader,$scope.account,$scope.buyers,$scope.ShowBuyer);
			}else if(view === "Buyer"){
				$scope.buyerview = true;
				loadtmpldata($scope.activitytableheader,$scope.buyer,$scope.activities,$scope.ShowActivity);
			}else if(view==="Activity"){
				$scope.activityview = true;
				loadtmpldata([],$scope.activity,[],null);
			}
			$scope.detailedview = ($scope.activityview)|($scope.buyerview)|($scope.accountview);
			$scope.tableview = ($scope.buyerview)|($scope.accountview)|($scope.accountstableview);
			if(addcurretview)
				($scope.breadcrumblist).push($scope.currentview);	
			// console.log($scope.accountview);
			$scope.currentview = view;
		}

		$scope.uploadFile = UtilityService.uploadFile;

});