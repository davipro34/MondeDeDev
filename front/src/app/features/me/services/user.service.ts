import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { User } from '../interfaces/user'

/**
 * Service for managing user data.
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: string = environment.backEndUrl + '/me';

  constructor(private http: HttpClient) { }

  /**
   * Retrieves the user data.
   * @returns An observable of type User.
   */
  public getUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}`);
  }

  /**
   * Updates the user data.
   * @param user The updated user object.
   * @returns An observable of type User.
   */
  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}`, user);
  }

  /**
   * Subscribes the user to a theme.
   * @param themeId The ID of the theme to subscribe to.
   * @returns An observable of type User.
   */
  public subscribeToTheme(themeId: number): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/themes/${themeId}`, {});
  }

  /**
   * Unsubscribes the user from a theme.
   * @param themeId The ID of the theme to unsubscribe from.
   * @returns An observable of type User.
   */
  public unsubscribeFromTheme(themeId: number): Observable<User> {
    return this.http.delete<User>(`${this.apiUrl}/themes/${themeId}`);
  }
}
