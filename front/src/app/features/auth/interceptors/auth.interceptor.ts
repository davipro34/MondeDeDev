import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { throwError } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor() {}

  /**
   * Intercepte les requêtes HTTP sortantes et ajoute l'en-tête d'autorisation si un jeton est disponible.
   */
  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    /**
     * Si l'URL de la requête contient '/login', la requête est renvoyée sans modification.
     * Si l'URL de la requête contient '/register', la requête est renvoyée sans modification.
     * Si un jeton est disponible dans le stockage local, l'en-tête d'autorisation est ajouté à la requête.
     * Sinon, une erreur HTTP avec le statut 401 est renvoyée.
     * @param request - La requête HTTP sortante.
     * @param next - Le gestionnaire HTTP pour passer la requête au prochain intercepteur ou au backend.
     * @returns Un observable de la réponse HTTP.
     * @throws Une erreur HTTP avec le statut 401 si aucun jeton n'est disponible.
     */
    if (request.url.includes('/login')) {
      return next.handle(request);
    }

    if (request.url.includes('/register')) {
      return next.handle(request);
    }

    const token = localStorage.getItem('token');

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
      return next.handle(request);
    } else {
      return throwError(() => new HttpErrorResponse({ status: 401, statusText: 'Veuillez vous connecter' }));
    }
  }
}