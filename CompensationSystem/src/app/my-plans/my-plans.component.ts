import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenStorageService } from '../_service/token-storage.service';
import { UserService } from '../_service/user.service';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

export interface plan {
  partnerName : number;
  compensationPlan : string,
  calculationMethod : string,
  minimum : string,
  maximum : string,
  percentage : string,
  fromDate : Date,
  toDate : Date
}

@Component({
  selector: 'app-my-plans',
  templateUrl: './my-plans.component.html',
  styleUrls: ['./my-plans.component.css']
})
export class MyPlansComponent implements OnInit {

  isLoggedIn = false;
  plans :any = [];
  employeeId : number = 0;

  columnsToDisplay = ['partnerName','compensationPlan','calculationMethod','minimum','maximum','percentage','fromDate','toDate'];
 
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort : MatSort;

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.employeeId = user.employeeId;
    }

    this.userService.getMyPlans(this.employeeId).subscribe(
      data => {
        this.plans = new MatTableDataSource<plan>(data);
        this.plans.paginator = this.paginator;
        this.plans.sort = this.sort;
      },
      err => {
        // this.content = JSON.parse(err.error).message;
        console.log(err);
      }
    );
  }

}
