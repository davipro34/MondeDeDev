import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { User } from '../../me/interfaces/user';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from './auth.service';
import { UserService } from '../../me/services/user.service';
import { catchError, firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private _user: BehaviorSubject<User | null > = new BehaviorSubject<User | null>(null);
  public user$: Observable<User | null> = this._user.asObservable();

  
  private _isLoggedSubject$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isLoggedIn$: Observable<any> = this._isLoggedSubject$.asObservable();

  constructor(private authService: AuthService,
              private userService: UserService) {
    this.initializeUser();
  }

  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this._isLoggedSubject$.next(true);
    this.initializeUser();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this._user.next(null);
    this._isLoggedSubject$.next(false);
  }

  public updateUser(updatedUser: User): void {
    this._user.next(updatedUser);
  }

  public async initializeUser(): Promise<void> {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const user = await firstValueFrom(this.userService.getUser().pipe(
          catchError(error => {
            if (error.status === 401) {
              localStorage.removeItem('token');
              this._isLoggedSubject$.next(false);
            }
            throw error;
          })
        ));
        this._user.next(user);
      } catch (error) {
        console.error('Erreur lors de l\'initialisation de l\'utilisateur', error);
      }
    }
  }
}