import { Injectable } from '@angular/core';
import { HttpClientModule, HttpRequest } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Account } from './account';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class AccountService {

  private accountsUrl: string;
  private buyersUrl: string;
  private activityUrl: string;
  private uploadCSVUrl: string;

  constructor(private http: HttpClient) {
    this.accountsUrl = 'http://localhost:8080/accounts?';
    this.buyersUrl = 'http://localhost:8080/buyers?';
    this.activityUrl = 'http://localhost:8080/activities?';
    this.uploadCSVUrl = 'http://localhost:8080/uploads';
  }
  
  public findAllAccounts(){
    return this.http.get(this.accountsUrl);
  }

  public findAccountByAny(params: HttpParams){
    return this.http.get(this.accountsUrl, {params});
  }

  public findAllBuyers(){
    return this.http.get(this.buyersUrl);
  }

  public findBuyerByAny(params: HttpParams){
    return this.http.get(this.buyersUrl, {params});
  }
 
  public findAllActivities(){
    return this.http.get(this.activityUrl);
  }

  public findActivityByAny(params: HttpParams){
    return this.http.get(this.activityUrl, {params});
  }

  public postFile(fileToUpload: File){
    const endpoint = this.uploadCSVUrl;
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
    const config = {
      headers: {
          
      },
    };
    return this.http.post(endpoint, formData, config);
    
  }

}