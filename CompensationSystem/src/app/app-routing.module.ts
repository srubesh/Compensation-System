import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { CreatePlanComponent } from './create-plan/create-plan.component';
import { CreateReportComponent } from './create-report/create-report.component';
import { LoginComponent } from './login/login.component';
import { MyPlansComponent } from './my-plans/my-plans.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'createplan', component: CreatePlanComponent },
  { path: 'myplans', component: MyPlansComponent },
  { path: 'createreport', component: CreateReportComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'adminpage', component: AdminPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
