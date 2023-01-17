import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_service/token-storage.service';
import { UserService } from '../_service/user.service';
import {MatSnackBar,MatSnackBarVerticalPosition} from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-plan',
  templateUrl: './create-plan.component.html',
  styleUrls: ['./create-plan.component.css']
})
export class CreatePlanComponent implements OnInit{

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService,private _snackBar: MatSnackBar) { }

  v_position : MatSnackBarVerticalPosition = 'top';
  isLoggedIn = false;
  
  employeeId : number = 0;
  message : any = null;

  create = {
    partnerName : "",
    compensationPlan : "",
    calculationMethod:"",
    minimum : "",
    maximum : "",
    percentage : "",
    fromDate: "" ,
    toDate : ""
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.employeeId = user.employeeId;
    }
  }

  delete(){
    this.create.minimum = "";
    this.create.maximum = "";
    this.create.percentage = "";
  }

  createPlan(){
    if(new Date(this.create.fromDate) <= new Date()){
      this._snackBar.open("FROM DATE should be greater than today's date",'', {
        duration: 3000,
        verticalPosition: this.v_position,
        panelClass: 'snackbar'
      });
    }
    else if(new Date(this.create.toDate) <=new Date(this.create.fromDate)){
      this._snackBar.open("TO DATE should be greater than FROM DATE",'', {
        duration: 3000,
        verticalPosition: this.v_position
      });
    }
    else{
      // console.log(this.create.partnerName);
      // console.log(this.create.compensationPlan);
      // console.log(this.create.calculationMethod);
      // console.log(this.create.minimum);
      // console.log(this.create.maximum);
      // console.log(this.create.percentage);
      // console.log(this.create.fromDate);
      // console.log(this.create.toDate);

      this.userService.createplan(this.create, this.employeeId).subscribe(
        data => {
        this.message = data;
        let snackBarRef = this._snackBar.open(this.message.message, "OK", {
          verticalPosition: this.v_position
        });
        snackBarRef.afterDismissed().subscribe(() => {
          window.location.reload();
        });
        },
        err => {
          //this.content = JSON.parse(err.error).message;
          console.log(err);
        }
      );
    }
  }

}
