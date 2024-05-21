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

  /**
   * URL de l'API backend pour l'authentification.
   */
  private backEndUrl: string = environment.backEndUrl + '/auth';

  constructor(private http: HttpClient) { }

  /**
   * Effectue une requête HTTP POST pour enregistrer un nouvel utilisateur.
   * @param register Les informations d'enregistrement de l'utilisateur.
   * @returns Un Observable qui ne renvoie aucune valeur.
   */
  public register(register: Register): Observable<void> {
    return this.http.post<void>(`${this.backEndUrl}/register`, register);
  }

  /**
   * Effectue une requête HTTP POST pour connecter un utilisateur.
   * @param loginRequest Les informations de connexion de l'utilisateur.
   * @returns Un Observable qui renvoie les informations de connexion réussie.
   */
  public login(loginRequest: Login): Observable<LoginSuccess> {
    return this.http.post<LoginSuccess>(`${this.backEndUrl}/login`, loginRequest);
  }
}
