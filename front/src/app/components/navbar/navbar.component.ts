import { Component } from '@angular/core';
import { SidebarService } from 'src/app/services/sidebar.service';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  constructor(private sidebarService: SidebarService) {}

  public toggleSidebar():void {
    this.sidebarService.toggleSidebar();
  }
}
