app.service('UtilityService',function($http){
    this.printhello = function(){
		console.log("hello");
	}
	this.urlgenerator = function(accountsform, baseurl){
		if(!((accountsform).page==="")){
			baseurl= baseurl + 'page=' + (accountsform).page;
		}
		if(!((accountsform).page_size==="")){
			baseurl= baseurl + '&page_size=' + (accountsform).page_size;
		}
		if(!((accountsform).name==="")){
			baseurl= baseurl + '&name=' + (accountsform).name;
		}
		if(!((accountsform).city==="")){
			baseurl= baseurl + '&city=' + (accountsform).city;
		}
		if(!((accountsform).state==="")){
			baseurl= baseurl + '&state=' + (accountsform).state;
		}
		if(!((accountsform).country==="")){
			baseurl= baseurl + '&country=' + (accountsform).country;
		}
		if(!((accountsform).ipdomain==="")){
				baseurl= baseurl + '&ip_domain=' + (accountsform).ipdomain;
			
			}
		if(!((accountsform).q==="")){
				baseurl= baseurl + '&q=' + (accountsform).q;
			
			}
		if(!((accountsform).type==="")){
				baseurl= baseurl + '&type=' + (accountsform).type;	
			}
		return baseurl;
    }
    
    this.resetobject=function(){
        accountsform={
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
        return accountsform;
    }

    this.createchart = function(datalist,element_id){
        //loadpersonadata(($scope.account).id);
        google.charts.load("current", {packages:["corechart"]});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable(Array.from(datalist));

            var options = {
            title: element_id,
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
            //console.log($scope.activitylist);
            var chart = new google.visualization.PieChart(document.getElementById(element_id));
            chart.draw(data, options);
        }
    }
    this.extractobjectfromquery = function(querystring, fields){
        accountsform = this.resetobject();
        for(field of fields){
            var field_start = (querystring).indexOf(field);
            if(field_start!=-1){
                var field_removed = (querystring).slice(field_start + field.length + 2);
                var field_val = field_removed.slice(0,field_removed.indexOf('"'));
                (accountsform)[field] = field_val;
                querystring = (querystring).replace(field + ':"' + field_val + '"', "");
            }
         }
         (accountsform).q = (querystring).trim();
         return accountsform;
    }

    this.uploadFile = function() {
        var fd = new FormData();  
        var file = document.getElementById("file").files[0];
        // console.log('file is ' );
        // console.dir(file);
        fd.append('type',file.type);
        fd.append('file',file);
        
        console.log(fd);
        $http({
            method : 'POST',
            url : "http://localhost:8080/uploadaccountlist",
            headers: {"Content-Type": undefined },
            data:fd,
            
        }).then(function successCallback(response) {
            alert((response.data).message);
            return response.message;
        }, function errorCallback(response) {
            alert((response.data).message);
            return response.message;
            // console.log(response.statusText);
        });
     };
});