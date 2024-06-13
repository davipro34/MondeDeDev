import { Injectable } from '@angular/core';
import {BehaviorSubject, firstValueFrom, Observable} from 'rxjs';
import { User } from '../../me/interfaces/user';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { ThemeService } from '../../themes/services/theme.service';
import { Theme } from '../../themes/interfaces/theme';
import { UserService } from '../../me/services/user.service';

/**
 * Service responsible for managing user sessions.
 */
@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private _user: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public user$: Observable<User | null> = this._user.asObservable();

  private _subscribedThemes: BehaviorSubject<Theme[]> = new BehaviorSubject<Theme[]>([]);
  public subscribedThemes$: Observable<Theme[]> = this._subscribedThemes.asObservable();

  private _isLoggedSubject$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isLoggedIn$: Observable<boolean> = this._isLoggedSubject$.asObservable();

  /**
   * Creates an instance of SessionService.
   * @param authService - The authentication service.
   * @param userService - The user service.
   * @param themeService - The theme service.
   */
  constructor(
    private authService: AuthService,
    private userService: UserService,
    private themeService: ThemeService
  ) {
    this.initializeUser();
  }

  /**
   * Logs in the user with the provided token.
   * @param token - The authentication token.
   */
  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this._isLoggedSubject$.next(true);
    this.initializeUser();
  }

  /**
   * Logs out the user.
   */
  public logOut(): void {
    localStorage.removeItem('token');
    this._user.next(null);
    this._isLoggedSubject$.next(false);
    this._subscribedThemes.next([]); // Clear subscribed themes
  }

  /**
   * Updates the user with the provided data.
   * @param updatedUser - The updated user data.
   */
  public updateUser(updatedUser: User): void {
    this._user.next(updatedUser);
    this.themeService.getThemes().subscribe(themes => {
      const subscribedThemes = themes.filter(theme => updatedUser.subscribedThemeIds.includes(theme.id));
      this._subscribedThemes.next(subscribedThemes);
    });
  }

  /**
   * Initializes the user session.
   * @returns A promise that resolves when the user session is initialized.
   */
  public async initializeUser(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const user = await firstValueFrom(
          this.userService.getUser().pipe(
            catchError(error => {
              if (error.status === 401) {
                localStorage.removeItem('token');
                this._isLoggedSubject$.next(false);
              }
              throw error;
            })
          )
        );

        if (user) {
          this._user.next(user);
          const themes: Theme[] = await firstValueFrom(this.themeService.getThemes());
          if (themes) {
            const subscribedThemes: Theme[] = themes.filter(theme => user.subscribedThemeIds.includes(theme.id));
            this._subscribedThemes.next(subscribedThemes);
          }
          this._isLoggedSubject$.next(true);
        }
      } catch (error) {
        throw error;
      }
    }
  }
}