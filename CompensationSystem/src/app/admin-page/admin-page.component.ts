import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenStorageService } from '../_service/token-storage.service';
import { UserService } from '../_service/user.service';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatSnackBar} from '@angular/material/snack-bar';

export interface user {
  employeeId : number;
  firstname : string,
  lastname : string,
  location : string,
  jobTitle : string,
  department : string,
  role : any
}

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent   implements OnInit {

  isLoggedIn = false;
  users : any;
 // employeeId : number = 0;
  content :any;
  columnsToDisplay = ['employeeId','firstname','lastname','location','jobTitle','department','role','action'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort : MatSort;

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService,private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      //this.employeeId = user.employeeId;
    }

    this.userService.getAllUsers().subscribe(
      data => {
        this.users = new MatTableDataSource<user>(data);
        this.users.paginator = this.paginator;
        this.users.sort = this.sort;
      },
      err => {
        // this.content = JSON.parse(err.error).message;
        console.log(err);
      }
    );
  }

  blockUser(user){
    this.userService.blockOrUnblockUser(user.employeeId,"yes").subscribe(
      data => {
        this.content = data;
        //alert(this.content.message);
        let snackBarRef = this._snackBar.open(this.content.message, "OK");
        snackBarRef.afterDismissed().subscribe(() => {
          window.location.reload();
        });
      },
      err => {
        // this.content = JSON.parse(err.error).message;
        console.log(err);
      }
    );
  }

  unblockUser(user){
    this.userService.blockOrUnblockUser(user.employeeId,"no").subscribe(
      data => {
        this.content = data;
        //alert(this.content.message);
        let snackBarRef = this._snackBar.open(this.content.message, "OK");
        snackBarRef.afterDismissed().subscribe(() => {
          window.location.reload();
        });
      },
      err => {
        // this.content = JSON.parse(err.error).message;
        console.log(err);
      }
    );
  }

}

