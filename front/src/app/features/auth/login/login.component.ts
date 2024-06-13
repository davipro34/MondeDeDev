/**
 * Represents the LoginComponent class.
 * This component is responsible for handling the login functionality.
 */
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Login } from '../interfaces/login';
import { LoginSuccess } from '../interfaces/login-success';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit, OnDestroy {
  formControls: { [key: string]: FormControl } = {
    usernameOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  };

  labels: { [key: string]: string } = {
    usernameOrEmail: "E-mail ou nom d'utilisateur",
    password: "Mot de passe",
  };

  controlNames: { [key: string]: string } = {
    usernameOrEmail: "votre e-mail ou nom d'utilisateur",
    password: "votre mot de passe",
  };

  errorMessages: { [key: string]: string } = {
    usernameOrEmail: '',
    password: '',
  };

  loginSubscription!: Subscription;

  constructor(private authService: AuthService, private router: Router, private sessionService: SessionService) {}

  /**
   * Initializes the component.
   */
  ngOnInit(): void {}

  /**
   * Performs the login operation.
   */
  login(): void {
    const loginRequest: Login = {
      usernameOrEmail: this.formControls['usernameOrEmail'].value,
      password: this.formControls['password'].value,
    };

    this.loginSubscription = this.authService.login(loginRequest)
      .subscribe({
        next: (response: LoginSuccess) => {
          this.sessionService.logIn(response.token);
          this.router.navigate(['/articles']).then(() => {});
        },
        error: error => {
          throw error;
        }
      });
  }

  /**
   * Handles the blur event of the input control.
   * @param controlName - The name of the control.
   */
  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  /**
   * Handles the form submission.
   */
  onSubmit(): void {
    if (this.formControls['usernameOrEmail'].valid && this.formControls['password'].valid) {
      this.login();
    }
  }

  /**
   * Cleans up resources before the component is destroyed.
   */
  ngOnDestroy(): void {
    if(this.loginSubscription){
      this.loginSubscription.unsubscribe();
    }
  }
}