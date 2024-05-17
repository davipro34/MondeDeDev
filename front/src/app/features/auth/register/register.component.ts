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
export class RegisterComponent implements OnInit, OnDestroy {

  private subscription: Subscription | undefined;

  /**
   * Contrôles de formulaire avec les validateurs correspondants.
   */
  formControls: { [key: string]: FormControl } = {
    username: new FormControl('', [Validators.required, Validators.minLength(4)]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$'),
    ]),
  };

  /**
   * Libellés des champs de formulaire.
   */
  labels: { [key: string]: string } = {
    username: 'Nom d’utilisateur',
    email: 'Adresse e-mail',
    password: 'Mot de passe',
  };

  /**
   * Messages d'erreur pour chaque champ de formulaire.
   */
  errorMessages: { [key: string]: string } = {
    username: '',
    email: '',
    password: '',
  };

  /**
   * Noms des contrôles de formulaire avec leurs descriptions.
   */
  controlNames: { [key: string]: string } = {
    username: 'un nom d’utilisateur avec au moins 4 caractères',
    email: 'une adresse e-mail valide',
    password: 'un mot de passe avec au moins 8 caractères, dont 1 lettre majuscule, 1 lettre minuscule, 1 chiffre et 1 caractère de ponctuation',
  };

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  /**
   * Gère l'événement de perte de focus d'un champ de formulaire.
   * @param controlName - Le nom du champ de formulaire.
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
   * Soumet le formulaire d'inscription.
   * @throws - Lève une exception en cas d'erreur lors de l'inscription.
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

  ngOnDestroy(): void {
    if (this.subscription) {
    this.subscription.unsubscribe();
    }
  }
}