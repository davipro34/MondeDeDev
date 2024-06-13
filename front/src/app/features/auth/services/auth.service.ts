/**
 * Service responsible for handling authentication-related operations.
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Register } from '../interfaces/register';
import { Login } from '../interfaces/login';
import { LoginSuccess } from '../interfaces/login-success';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private backEndUrl: string = environment.backEndUrl + '/auth';

  constructor(private http: HttpClient) { }

  /**
   * Registers a new user.
   * @param register - The registration details of the user.
   * @returns An Observable that emits void.
   */
  public register(register: Register): Observable<void> {
    return this.http.post<void>(`${this.backEndUrl}/register`, register);
  }

  /**
   * Logs in a user.
   * @param loginRequest - The login details of the user.
   * @returns An Observable that emits the login success response.
   */
  public login(loginRequest: Login): Observable<LoginSuccess> {
    return this.http.post<LoginSuccess>(`${this.backEndUrl}/login`, loginRequest);
  }
}
