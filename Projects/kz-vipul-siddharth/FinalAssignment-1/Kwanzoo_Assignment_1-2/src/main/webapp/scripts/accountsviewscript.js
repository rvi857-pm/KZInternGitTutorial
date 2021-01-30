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
			id:"",
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
						
                    }, function errorCallback(response) {
      
                        console.log(response.statusText);
                    });
   		};
   		$scope.AccountQuery = function(){
   			urlgenerator();
			//console.log($scope.url);
   			$http({
                        method : 'GET',
                        url : $scope.url
                    }).then(function successCallback(response) {
						reseturl();
                        $scope.accounts = response.data;   
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
			console.log($scope.url);
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
			($scope.account).id = ($scope.accounts[index]).id;
			($scope.account).name = ($scope.accounts[index]).name;
			($scope.account).city = ($scope.accounts[index]).city;
			($scope.account).state = ($scope.accounts[index]).state;
			($scope.account).country = ($scope.accounts[index]).country;
			($scope.account).type = ($scope.accounts[index]).type;
			($scope.account).salesforce_id = ($scope.accounts[index]).salesforce_id;
			($scope.account).ip_domain = ($scope.accounts[index]).ip_domain;
			// loadlocationdata(($scope.account).id);
			// //createlocationchart();
			// loadpersonadata(($scope.account).id);
			// loadactivitydata(($scope.account).id);
			loadaccountdata(($scope.account).id);
			$scope.accountview=true;
			//console.log(name);
		};
   		
		$scope.ProcessString= function(){
			var tempstring = $scope.querystring;
			if(tempstring.length == 0){
				resetobject();
				$scope.AccountQuery();
			}
			else{
				resetobject();
				for(field of $scope.fields){
					var field_start = ($scope.querystring).indexOf(field);
					if(field_start!=-1){
						var field_removed = ($scope.querystring).slice(field_start + field.length + 2);
						var field_val = field_removed.slice(0,field_removed.indexOf('"'));
						($scope.accountsform)[field] = field_val;
						$scope.querystring = ($scope.querystring).replace(field + ':"' + field_val + '"', "");
					}
				 }
				 ($scope.accountsform).q = ($scope.querystring).trim();
				 $scope.querystring = tempstring;
				
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
					processlocationlist(acclocationlist);
					processpersonalist(accpersonalist);
					processactivitylist(accactivitylist);
					createchart($scope.activitylist,'activity_piechart');
					createchart($scope.locationlist,'location_piechart');
					createchart($scope.personalist,'persona_piechart');
				}, function errorCallback(response) {

					console.log(response.statusText);
				});
			}
		}

		function createchart(datalist,element_id){
			//loadpersonadata(($scope.account).id);
			google.charts.load("current", {packages:["corechart"]});
			google.charts.setOnLoadCallback(drawChart);
			function drawChart() {
				var data = google.visualization.arrayToDataTable(Array.from(datalist));

				var options = {
				title: 'Account activity Count',
				is3D: true,
				legend:{
					maxLines: 5
				},
				sliceVisibilityThreshold: 0,
				forceIFrame:true,
				chartArea:{
					left:15
				}
				};
				console.log($scope.activitylist);
				var chart = new google.visualization.PieChart(document.getElementById(element_id));
				chart.draw(data, options);
			}
		}




});