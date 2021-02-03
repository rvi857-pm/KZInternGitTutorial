import { NULL_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Account } from '../account';
import { Buyer } from '../buyer';
import { Activity } from '../activity';
import { AccountService } from '../account.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ModalComponent } from '../_modal/modal.component';
import { ModalModule } from '../_modal';
import { ModalService } from '../_modal';
import { MatDialog } from '@angular/material/dialog';
import * as c3 from '../../../node_modules/c3';
import { ElementRef, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  inputJSON : any;
  accounts: Account[];
  buyers: Buyer[];
  activities: Activity[];
  pageOfItems: Array<any>;
  pageSizeSelector: string;
  pageSize : number;
  initialPage = 1;
  searchInput: string;
  currentAccount: Account = null;
  activityView:boolean = true;
  locationView:boolean = true;
  personaView:boolean = true;
  buyerTableView:boolean = true;
  activityTableView:boolean = true;
  currentBuyer: Buyer = null;
  currentActivity: Activity = null;
  fileToUpload:File = null;
  uploadStatus:boolean = false;

  constructor(modalModule: ModalModule, public modalService: ModalService, private accountService: AccountService) {
  }

  ngOnInit() {
    this.accountService.findAllAccounts().subscribe(data => {
      this.inputJSON = data;
      this.accounts = this.inputJSON.entity_list;
      console.log(this.accounts);
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    // reset page if items array has changed
    if (changes.items.currentValue !== changes.items.previousValue) {
        this.onChangePage(this.pageOfItems);
    }
  }
  generatePieChart(activityCount:any, locationCount: any, personaCount: any){
    if(activityCount['ad_clicks'] == 0 && activityCount['form_fills'] == 0 && activityCount['website_visits'] == 0 && activityCount['live_chats'] == 0){
      this.activityView = false;
    }
    else{
      
      var label = [];
      var data = {};
      var jsonData = activityCount;
      // console.log("hello");
      // console.log(jsonData);

      for(var key in jsonData){
        var activityString:string = key;
        if(key !== 'total'){
          data[activityString] = jsonData[key];
          label.push(activityString);
        }
      }
      let chart = c3.generate({
        bindto: '#activityChart',
            data: {
                json: [data],
                keys: {
                  value:label,
                },
                type: 'pie'
            }
      });
      this.activityView = true;
    }
    if(locationCount.length !== 0){
      
      var label = [];
      var data = {};
      var jsonData = locationCount;


      jsonData.forEach(function(e){
        var geoString:string = '';
        geoString += e.city + ', ' + e.state + ', ' + e.country;
        data[geoString] = e.count;
        label.push(geoString);
      })
      let chart = c3.generate({
        bindto: '#locationChart',
            data: {
                json: [data],
                keys: {
                  value:label,
                },
                type: 'pie'
            }
      });
        this.locationView = true;
    }
    else{
      this.locationView = false;
    }
    if(personaCount.length !== 0){
      
      var label = [];
      var data = {};
      var jsonData = personaCount;


      jsonData.forEach(function(e){
        var personaString:string = '';
        personaString += e.job_level + ', ' + e.job_function;
        data[personaString] = e.count;
        label.push(personaString);
      })
      let chart = c3.generate({
        bindto: '#personaChart',
            data: {
                json: [data],
                keys: {
                  value:label,
                },
                type: 'pie'
            }
        });
        this.personaView = true;
    }
    else{
      this.personaView = false;
    }
  }

  clearPage(){
    this.searchInput = '';
    this.ngOnInit();
  }

  takeTextInput(filterValue: string) {
	  this.searchInput = filterValue;
	//console.log(this.searchInput);
  }

  parseString(){
	  let inputStr = this.searchInput;
	  let listOfFilters:string[] = ['name', 'ip_domain', 'city', 'state', 'country', 'type', 'salesforce_id'];
	  let params = new HttpParams();
	  
	  for(let i = 0; i < listOfFilters.length; i++){
		  let firstIndex = inputStr.indexOf(listOfFilters[i]);
		  if(firstIndex > -1){
			  firstIndex += listOfFilters[i].length + 2;
		  }
		  else{
			  continue;
		  }
		  let filteredString = inputStr.slice(firstIndex, inputStr.indexOf('"', firstIndex));
      inputStr = inputStr.replace('"' + filteredString + '"', '').replace(listOfFilters[i] + ':', '');
		  
		  params = params.append(listOfFilters[i], filteredString);
    }
      //console.log(inputStr);
      inputStr = inputStr.trim();
      //console.log(inputStr);

	  	if(inputStr !== ''){
			  params = params.append('q', inputStr);
    }
    //console.log(params.getAll('q'));
    console.log(params);
    return params;
    
  }

  updateAccountVariables(temporaryJson: any, activityCount: any, personaCount: any, locationCount: any){
    let params = new HttpParams();
    params = params.append('name', this.currentAccount.name);
    params = params.append('metrics', 'all');
    this.accountService.findAccountByAny(params).subscribe(data => {
      temporaryJson = data;
      console.log(temporaryJson);
      activityCount = temporaryJson.entity_list[0].activity_count;
      personaCount = temporaryJson.entity_list[0].persona_count;
      locationCount = temporaryJson.entity_list[0].location_count;  
      this.generatePieChart(activityCount, locationCount, personaCount);
      
      var paramsForBuyer = new HttpParams();
      paramsForBuyer = paramsForBuyer.append('account_id', this.currentAccount.id);
      var tempJSON : any;
      this.accountService.findBuyerByAny(paramsForBuyer).subscribe(dataBuyer => {
        tempJSON = dataBuyer;
        this.buyers = tempJSON.entity_list;
        this.buyerTableView = (this.buyers.length !== 0) ? true : false;
      });
    });
    
  }

  updateBuyerVariables(){
    let params = new HttpParams();
    params = params.append('buyer_id', this.currentBuyer.id);
    var temporaryJson: any;
  
    this.accountService.findActivityByAny(params).subscribe(data => {
      temporaryJson = data;
      console.log(temporaryJson);
      this.activities = temporaryJson.entity_list;
      this.activityTableView = (this.activities.length !== 0) ? true : false;
    });
    
  }


  refreshPage(){
    //console.log("clicked1");
    let params = this.parseString();

    //console.log("clicked2");
    this.accountService.findAccountByAny(params).subscribe(data => {
    this.inputJSON = data;
    this.accounts = this.inputJSON.entity_list;
      
    });
  }

  setPageSize(){
    if(this.pageSizeSelector === 'PageSize5'){
      this.pageSize = 5;
    }
    else if(this.pageSizeSelector === 'PageSize10'){
      this.pageSize = 10;
    }
    else if(this.pageSizeSelector === 'PageSize15'){
      this.pageSize = 15;
    }
    else if(this.pageSizeSelector === 'PageSize20'){
      this.pageSize = 20;
    }
    else if(this.pageSizeSelector === 'PageSize25'){
      this.pageSize = 25;
    }
    else if(this.pageSizeSelector === 'PageSize30'){
      this.pageSize = 30;
    }
    else{
      this.pageSize = 10;
    }
  }
  
  onChangePage(pageOfItems: Array<any>) {
    // update current page of items
    this.pageOfItems = pageOfItems;
  }

  openAccountModal(clickedAccount: Account) {
    var activityCount: any = [];
    var locationCount: any = [];
    var personaCount: any = [];
    var temporaryJson: any;
    this.modalService.open('accountModal');
    this.currentAccount = clickedAccount;
    this.updateAccountVariables(temporaryJson, activityCount, locationCount, personaCount);
  
  }

  openBuyerModal(clickedBuyer: Buyer) {
    var temporaryJson: any;
    this.closeModal('accountModal');
    this.modalService.open('buyerModal');
    this.currentBuyer = clickedBuyer;
    this.updateBuyerVariables();
  
  }


  closeModal(id: string) {
    
    if(id ==='buyerModal'){
      this.modalService.close(id);
      this.openAccountModal(this.currentAccount);  
    }
    else{
      this.modalService.close(id);    
    }
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToAccounts() {
    this.accountService.postFile(this.fileToUpload).subscribe(data => {
      console.log("successfully uploaded");
    }, error => {
      alert("Successfully uploaded");
      this.uploadStatus = true;
      this.clearPage();
      console.log(error);
    });
  }
}
