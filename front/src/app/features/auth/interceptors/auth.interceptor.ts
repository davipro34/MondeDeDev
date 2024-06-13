import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { throwError } from "rxjs";

@Injectable()
/**
 * Interceptor for adding authentication token to outgoing HTTP requests.
 */
export class AuthInterceptor implements HttpInterceptor {
  constructor() {}

  /**
   * Intercepts the outgoing HTTP requests and adds the authentication token if available.
   * @param request - The outgoing HTTP request.
   * @param next - The next interceptor in the chain.
   * @returns An Observable of the HTTP response.
   */
  public intercept(request: HttpRequest<any>, next: HttpHandler) {

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