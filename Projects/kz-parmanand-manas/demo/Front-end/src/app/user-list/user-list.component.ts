import { NULL_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user-service.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  pageOfItems: Array<any>;
  pageSizeSelector: string;
  pageSize : number;
  initialPage = 1;
  searchInput: string;
 
  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
    this.users = data;
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    // reset page if items array has changed
    if (changes.items.currentValue !== changes.items.previousValue) {
        this.onChangePage(this.pageOfItems);
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
    return params;
    
  }

  refreshPage(){
    console.log("clicked1");
    let params = this.parseString();

    console.log("clicked2");
    this.userService.findByAny(params).subscribe(data => {
    this.users = data;
      
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
}