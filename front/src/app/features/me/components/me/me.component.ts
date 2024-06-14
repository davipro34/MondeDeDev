/**
 * Represents the MeComponent class.
 * This component is responsible for displaying and managing the user profile information.
 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {SessionService} from "../../../auth/services/session.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/user";
import {Router} from "@angular/router";
import { Theme } from 'src/app/features/themes/interfaces/theme';

@Component({
  selector: 'app-me',
  standalone: false,
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {
  subscribedThemes: Theme[] = [];
  user: User | null = null;
  private themesSubscription: Subscription | null = null;
  private userSubscription: Subscription | null = null;

  formControls: { [key: string]: FormControl } = {
    username: new FormControl('', [Validators.required, Validators.minLength(4)]),
    email: new FormControl('', [Validators.email, Validators.required]),
  };

  labels: { [key: string]: string } = {
    username: 'Nom d’utilisateur',
    email: 'Adresse e-mail',
  };

  controlNames: { [key: string]: string } = {
    username: 'un nom d’utilisateur avec au moins 4 caractères',
    email: 'une adresse e-mail valide',
  };

  errorMessages: { [key: string]: string } = {
    username: '',
    email: '',
  };

  constructor(private sessionService: SessionService,
              private userService: UserService, private router: Router) {}

  /**
   * Initializes the component.
   * Subscribes to the sessionService's subscribedThemes$ and user$ observables.
   * Sets the initial values for the form controls based on the user's information.
   */
  ngOnInit(): void {
    this.themesSubscription = this.sessionService.subscribedThemes$.subscribe(themes => {
      this.subscribedThemes = themes;
      this.sortSubscribedThemesAlphabetically();
    });

    this.userSubscription = this.sessionService.user$.subscribe(user => {
      this.user = user;
      if (this.user) {
        console.log('user', this.user);
        this.formControls['username'].setValue(this.user.username);
        this.formControls['email'].setValue(this.user.email);
      } else {
        this.formControls['username'].setValue('');
        this.formControls['email'].setValue('');
      }
    });
  }

  private sortSubscribedThemesAlphabetically(): void {
    this.subscribedThemes.sort((a, b) => a.title.localeCompare(b.title));
  }

  /**
   * Handles the onBlur event for the form controls.
   * Marks the control as touched and sets the error message if the control is required and empty.
   * @param controlName - The name of the form control.
   */
  onBlur(controlName: string) {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  /**
   * Handles the onSubmit event for the form.
   * Updates the user's information if the form controls are valid.
   * Logs out the user if the user is not logged in.
   */
  onSubmit() {
    if (this.formControls["username"].valid && this.formControls['email'].valid) {
      if (this.user && this.user.id !== undefined && this.user.id !== null) {
        const updatedUser: User = {
          id: this.user.id,
          username: this.formControls['username'].value,
          email: this.formControls['email'].value,
          password: this.user.password,
          subscribedThemeIds: this.user.subscribedThemeIds
        };
        this.userService.updateUser(updatedUser).subscribe((user) => {
          this.sessionService.updateUser(user);
        });
      } else {
         this.sessionService.logOut();
      }
    }
  }

  /**
   * Handles the onLogout event.
   * Logs out the user and navigates to the login page.
   */
  onLogout(){
    this.sessionService.logOut();
    this.router.navigate(['/login']).then(
      () => {
      }
    );
  }

  /**
   * Handles the onUnsubscribe event for a theme.
   * Unsubscribes the user from the specified theme.
   * @param themeId - The ID of the theme to unsubscribe from.
   */
  onUnsubscribe(themeId : number){
    this.userService.unsubscribeFromTheme(themeId).subscribe((updatedUser) => {
      this.sessionService.updateUser(updatedUser);
    });
  }

  /**
   * Cleans up the component.
   * Unsubscribes from the themesSubscription and userSubscription observables.
   */
  ngOnDestroy(): void {
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }
}