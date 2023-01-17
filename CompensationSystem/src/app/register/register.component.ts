import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  form: any = {
    employeeId: null,
    firstname: null,
    lastname: null,
    location: null,
    jobTitle: null,
    department: null,
    role: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  isPhoneValid = true;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    //console.log(this.form);
    const { employeeId, firstname, lastname, location,jobTitle,department,role, password } = this.form;
    console.log(employeeId +" "+ firstname +" "+ lastname +" "+ location +" "+ jobTitle +" "+ department +" "+ role +" "+ password);
    this.authService.register(employeeId, firstname, lastname, location,jobTitle,department,role, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
