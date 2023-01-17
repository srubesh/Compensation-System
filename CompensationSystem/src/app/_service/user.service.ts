import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/compensationplan/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }

  ///createplan/{userId}
  createplan(create : any, userId : number){

    return this.http.post(API_URL +'createplan/'+userId,create, { responseType: 'json' });
  }

  ///getmyplans/{employeeId}
  getMyPlans(employeeId : number): Observable<any> {
    return this.http.get(API_URL + 'getmyplans/'+employeeId, { responseType: 'json' });
  }
  
  //getallplans
  getAllPlans(): Observable<any> {
    return this.http.get(API_URL + 'getallplans/', { responseType: 'json' });
  }

  //getAllUsers
  getAllUsers(): Observable<any> {
    return this.http.get(API_URL + 'getAllUsers/', { responseType: 'json' });
  }
  
  blockOrUnblockUser(employeeId : number,block : string){
    return this.http.put(API_URL + 'blockuser/'+employeeId+"/"+block, { responseType: 'json' });
  }

  ///------------------------------------------------------
  //readers/{emailId}/books
  getSubscribedBooks(readerEmail : string): Observable<any> {
    return this.http.get(API_URL + 'readers/'+readerEmail+"/books", { responseType: 'json' });
  }

  //readers/{email-id}/books/{subscription-id}/cancel-subscription
  unSubscriveBook(readerEmail : string, subscriptionId : string){
    return this.http.put(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/cancel-subscription", { responseType: 'json' });
  }

  ///{book-id}/subscribe
  subscribeBook(email : string, bookId : number){
    return this.http.post(API_URL +bookId+'/subscribe',{
      bookId,
      email
    }, { responseType: 'json' });
  }


  //readers/{emailId}/books/{subscription-id}/read
  getBookContent(readerEmail : string, subscriptionId : string){
    return this.http.get(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/read", { responseType: 'json' });
  }

  //all/authors
  // getAllAuthors(): Observable<any> {
  //   return this.http.get(API_URL + 'all/authors', { responseType: 'json' });
  // }

  //'searchBook?title=&author=&publishedDate=&publisher='
  searchBook(searchQuery : any ):  Observable<any> {
    if(searchQuery === null || searchQuery === undefined)
      return this.http.get(API_URL + 'searchBook?title=&author=&publishedDate=&publisher=', { responseType: 'json' });
    return this.http.get(API_URL + searchQuery, { responseType: 'json' });
  }

  // getModeratorBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'mod', { responseType: 'text' });
  // }

  // getAdminBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'admin', { responseType: 'text' });
  // }
}
