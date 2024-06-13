import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';

/**
 * Service for managing themes.
 */
@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private apiUrl: string = environment.backEndUrl + '/themes';
  private themes: Theme[] | null = null;

  constructor(private http: HttpClient) { }

  /**
   * Retrieves the list of themes.
   * If the themes have already been fetched, it returns them from the cache.
   * Otherwise, it makes an HTTP GET request to fetch the themes from the server.
   * @returns An observable that emits the list of themes.
   */
  getThemes(): Observable<Theme[]> {
    if (this.themes) {
      return of(this.themes);
    } else {
      return this.http.get<Theme[]>(this.apiUrl).pipe(
        tap(themes => this.themes = themes)
      );
    }
  }
}