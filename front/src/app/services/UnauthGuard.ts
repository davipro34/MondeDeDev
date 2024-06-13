import { Injectable } from "@angular/core";
import {Router, UrlTree} from "@angular/router";
import { Observable } from "rxjs";
import {map} from "rxjs/operators";
import { SessionService } from "../features/auth/services/session.service";

@Injectable({ providedIn: 'root' })
export class UnauthGuard {
  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) { }

  public canActivate(): Observable<boolean | UrlTree> {
    return this.sessionService.isLoggedIn$.pipe(
      map(isLoggedIn => {
        if (isLoggedIn) {
          return this.router.parseUrl('/articles');
        }
        return !isLoggedIn;
      })
    );
  }
}