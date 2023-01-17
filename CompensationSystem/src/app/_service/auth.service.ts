import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8081/compensationplan/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(employeeId: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      employeeId,
      password
    }, httpOptions);
  }

  register(employeeId: string, firstname: string,lastname: string,location: string,jobTitle: string,department: string,role: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      employeeId,
      firstname,
      lastname,
      location,
      jobTitle,
      department,
      role,
      password
    }, httpOptions);
  }
}
