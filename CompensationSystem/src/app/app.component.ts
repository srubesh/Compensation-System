import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_service/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'CompensationSystem';

  private role: string = "";
  isLoggedIn = false;
  showPlanCreaterPage = false;
  showReportCreaterPage = false;
  showAdminPage = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user.role;


      this.showPlanCreaterPage = this.role.includes('PLAN_USER');
      this.showReportCreaterPage = this.role.includes('REPORT_USER');
      this.showAdminPage = this.role.includes('ADMIN_USER');
      this.username = user.firstname;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }


}
