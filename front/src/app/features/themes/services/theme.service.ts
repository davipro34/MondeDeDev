import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private apiUrl: string = environment.backEndUrl + '/themes';
  private themes: Theme[] | null = null;

  constructor(private http: HttpClient) { }

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