import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <router-outlet></router-outlet>
  `,
  styles: [`
    :host {
      position: fixed;
      inset: 0 0 0 0;
      height: 100%;
      width: 100%;
    }
  `],
})
export class AppComponent {

}