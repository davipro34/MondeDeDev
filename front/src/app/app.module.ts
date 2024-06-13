import { NgModule, LOCALE_ID, ErrorHandler } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './features/auth/interceptors/auth.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from "./app.component";
import { HomeComponent } from './components/home/home.component';
import { MatCardModule } from '@angular/material/card';
import { NgOptimizedImage } from "@angular/common";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { MatIcon } from "@angular/material/icon";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from "@angular/material/input";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { MatMenuModule } from "@angular/material/menu";
import { MatOption, MatSelect } from "@angular/material/select";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbar } from "@angular/material/toolbar";
import { MatNavList } from "@angular/material/list";
import { MatDivider } from "@angular/material/divider";
import { LoginComponent } from "./features/auth/login/login.component";
import { RegisterComponent } from "./features/auth/register/register.component";
import { MeComponent } from "./features/me/components/me/me.component";
import { HeaderComponent } from './components/header/header.component';
import {ListComponent as ThemeListComponent} from "./features/themes/components/list/list.component";
import localeFr from '@angular/common/locales/fr';
import { GlobalErrorHandler } from './services/globalerrorhandler.service';
import {ListComponent as ArticleListComponent} from "./features/articles/components/list/list.component";
import {DetailComponent as ArticleDetailComponent } from "./features/articles/components/detail/detail.component";
import {FormComponent as ArticleFormComponent} from "./features/articles/components/form/form.component";

registerLocaleData(localeFr, 'fr');

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    MeComponent,
    ThemeListComponent,
    ArticleDetailComponent,
    ArticleListComponent,
    ArticleFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    HttpClientModule,
    NgOptimizedImage,
    MatIcon,
    MatFormFieldModule,
    MatInput,
    MatProgressSpinner,
    MatMenuModule,
    MatSelect,
    MatOption,
    FormsModule,
    MatSidenavModule,
    MatToolbar,
    MatNavList,
    MatDivider
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr'},
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: ErrorHandler, useClass: GlobalErrorHandler }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}