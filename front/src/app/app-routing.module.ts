import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { LoginComponent } from './features/auth/login/login.component';
import { HeaderComponent } from './components/header/header.component';
import { MeComponent } from './features/me/components/me/me.component';
import { ListComponent as ThemeListComponent } from "./features/themes/components/list/list.component";
import { ListComponent as ArticleListComponent } from "./features/articles/components/list/list.component";
import {FormComponent as ArticleFormComponent} from "./features/articles/components/form/form.component";
import {DetailComponent as ArticleDetailComponent} from "./features/articles/components/detail/detail.component";
import { UnauthGuard } from './services/UnauthGuard';
import { AuthGuard } from './services/AuthGuard';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
  {
    path: '',
    component: HeaderComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'me', component: MeComponent },
      { path: 'themes', component: ThemeListComponent },
      { path: 'articles', component: ArticleListComponent },
      { path: 'articles/new', component: ArticleFormComponent },
      { path: 'articles/:id', component: ArticleDetailComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
