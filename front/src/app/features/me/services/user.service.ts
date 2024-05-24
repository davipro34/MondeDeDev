import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { User } from '../interfaces/user'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: string = environment.backEndUrl + '/me';

  constructor(private http: HttpClient) { }

  public getUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}`)
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}`, user);
  }
}
