import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Login } from '../interfaces/login';

/**
 * Composant de connexion.
 */
@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit, OnDestroy {
  /**
   * Contrôles du formulaire.
   */
  formControls: { [key: string]: FormControl } = {
    usernameOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  };

  /**
   * Libellés des champs du formulaire.
   */
  labels: { [key: string]: string } = {
    usernameOrEmail: 'E-mail ou nom d’utilisateur',
    password: 'Mot de passe',
  };

  /**
   * Noms des contrôles du formulaire.
   */
  controlNames: { [key: string]: string } = {
    usernameOrEmail: 'votre e-mail ou nom d’utilisateur',
    password: 'votre mot de passe',
  };

  /**
   * Messages d'erreur des champs du formulaire.
   */
  errorMessages: { [key: string]: string } = {
    usernameOrEmail: '',
    password: '',
  };

  /**
   * Abonnement à la connexion.
   */
  loginSubscription!: Subscription;

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Méthode appelée lors de l'initialisation du composant.
   */
  ngOnInit(): void {}

  /**
   * Méthode de connexion.
   */
  login(): void {
    /**
     * Requête de connexion.
     * @param usernameOrEmail - L'e-mail ou le nom d'utilisateur.
     * @param password - Le mot de passe.
     */
    const loginRequest: Login = {
      usernameOrEmail: this.formControls['usernameOrEmail'].value,
      password: this.formControls['password'].value,
    };

    this.loginSubscription = this.authService.login(loginRequest)
      .subscribe({
        /**
         * Fonction appelée en cas de succès de la connexion.
         */
        next: () => {
          this.router.navigate(['/me']).then(
            () => {
            }
          );
        },
        /**
         * Fonction appelée en cas d'erreur lors de la connexion.
         * @param error - L'erreur survenue.
         * @throws L'erreur survenue.
         */
        error: error => {
          throw error;
        }
      });
  }

  /**
   * Méthode appelée lorsqu'un champ perd le focus.
   * @param controlName - Le nom du champ.
   */
  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  /**
   * Méthode appelée lors de la soumission du formulaire.
   */
  onSubmit(): void {
    if (this.formControls['usernameOrEmail'].valid && this.formControls['password'].valid) {
      this.login();
    }
  }

  /**
   * Méthode appelée lors de la destruction du composant.
   */
  ngOnDestroy(): void {
    if(this.loginSubscription){
      this.loginSubscription.unsubscribe();
    }
  }
}