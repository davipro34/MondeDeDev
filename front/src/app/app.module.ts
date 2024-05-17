import { LOCALE_ID, NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatIcon} from "@angular/material/icon";
import { RegisterComponent } from './features/auth/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './features/auth/services/auth.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './features/auth/interceptors/auth.interceptor';
import { LoginComponent } from './features/auth/login/login.component';

/**
 * Module principal de l'application.
 */
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule, // Module pour le navigateur
    AppRoutingModule, // Module pour le routage
    BrowserAnimationsModule, // Module pour les animations
    ReactiveFormsModule, // Module pour les formulaires réactifs
    MatCardModule, // Module pour les cartes
    MatButtonModule, // Module pour les boutons
    MatFormFieldModule, // Module pour les champs de formulaire
    MatInputModule, // Module pour les champs de saisie
    MatIcon, // Module pour les icônes
    FormsModule, // Module pour les formulaires
    HttpClientModule // Module pour les requêtes HTTP
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr'}, // Fournisseur pour la localisation
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, // Fournisseur pour l'intercepteur HTTP
    AuthService // Service d'authentification
  ],
  bootstrap: [AppComponent], // Composant racine de l'application
})
export class AppModule {}
