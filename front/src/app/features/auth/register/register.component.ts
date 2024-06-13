import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Register} from "../interfaces/register";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
/**
 * Represents the RegisterComponent class.
 * This component is responsible for handling user registration.
 */
export class RegisterComponent implements OnInit, OnDestroy {

  private subscription: Subscription | undefined;

  formControls: { [key: string]: FormControl } = {
    username: new FormControl('', [Validators.required, Validators.minLength(4)]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$'),
    ]),
  };

  labels: { [key: string]: string } = {
    username: 'Nom d’utilisateur',
    email: 'Adresse e-mail',
    password: 'Mot de passe',
  };

  errorMessages: { [key: string]: string } = {
    username: '',
    email: '',
    password: '',
  };

  controlNames: { [key: string]: string } = {
    username: 'un nom d’utilisateur avec au moins 4 caractères',
    email: 'une adresse e-mail valide',
    password: 'un mot de passe avec au moins 8 caractères, dont 1 lettre majuscule, 1 lettre minuscule, 1 chiffre et 1 caractère de ponctuation',
  };

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Lifecycle hook that is called after the component has been initialized.
   */
  ngOnInit(): void {}

  /**
   * Event handler for the onBlur event of the form controls.
   * Updates the error message based on the validation result.
   * @param controlName - The name of the form control.
   */
  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    if (control.hasError('required')) {
      this.errorMessages[controlName] = `Veuillez saisir ${this.controlNames[controlName]}`;
    } else if (control.hasError('pattern')) {
      this.errorMessages[controlName] = 'Le mot de passe ne correspond pas au modèle requis';
    } else {
      this.errorMessages[controlName] = '';
    }
  }

  /**
   * Event handler for the onSubmit event of the form.
   * Submits the registration request if the form is valid.
   */
  onSubmit(): void {
    if (this.formControls["username"].valid && this.formControls['email'].valid && this.formControls['password'].valid) {
      const registerRequest: Register = {
        username: this.formControls['username'].value,
        email: this.formControls['email'].value,
        password: this.formControls['password'].value,
      };
      this.subscription = this.authService.register(registerRequest).subscribe({
        next: () => {
          this.router.navigate(['/login']).then(
            () => {}
          );
        },
        error: error => {
          throw error;
        }
      });
    }
  }

  /**
   * Lifecycle hook that is called when the component is about to be destroyed.
   * Unsubscribes from the subscription if it exists.
   */
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}