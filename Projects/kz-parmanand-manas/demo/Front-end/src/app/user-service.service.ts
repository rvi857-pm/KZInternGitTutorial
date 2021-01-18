import { Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { User } from './user';
import { Observable } from 'rxjs';

@Injectable()
export class UserService {

  private usersUrl: string;
  
  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/accounts?';
  }
  
  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public findByAny(params: HttpParams) : Observable<User[]>{
    return this.http.get<User[]>(this.usersUrl, {params});
  }
  
}