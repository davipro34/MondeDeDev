import { Component } from '@angular/core';
import { SidebarService } from 'src/app/services/sidebar.service';

/**
 * Represents the navbar component.
 */
@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  constructor(private sidebarService: SidebarService) {}

  /**
   * Toggles the sidebar.
   */
  public toggleSidebar(): void {
    this.sidebarService.toggleSidebar();
  }
}
