import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { LoginComponent } from './features/auth/login/login.component';

/**
 * Module de routage de l'application.
 */
const routes: Routes = [
  /**
   * Route pour la page d'accueil.
   */
  { path: '', component: HomeComponent },
  
  /**
   * Route pour la page de connexion.
   */
  { path: 'login', component: LoginComponent},
  
  /**
   * Route pour la page d'inscription.
   */
  { path: 'register', component: RegisterComponent}
];

@NgModule({
  /**
   * Importe les routes dans le module de routage.
   */
  imports: [RouterModule.forRoot(routes)],
  
  /**
   * Exporte le module de routage pour qu'il puisse être utilisé par d'autres modules.
   */
  exports: [RouterModule],
})
export class AppRoutingModule {}
