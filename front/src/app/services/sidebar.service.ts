import { Injectable } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

/**
 * Service for managing the sidebar functionality.
 */
@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  private sidenav!: MatSidenav;
  private isOpen: boolean = false;

  /**
   * Sets the MatSidenav instance for the sidebar.
   * @param sidenav The MatSidenav instance.
   */
  public setSidenav(sidenav: MatSidenav) {
    this.sidenav = sidenav;
  }

  /**
   * Toggles the sidebar open/close state.
   */
  public toggleSidebar() {
    this.sidenav.toggle().then(() => {
      this.isOpen = this.sidenav.opened;
    });
  }

  /**
   * Closes the sidebar.
   */
  public closeSidebar() {
    this.sidenav.close().then(() => {
      this.isOpen = this.sidenav.opened;
    });
  }

  /**
   * Checks if the sidebar is currently open.
   * @returns A boolean indicating if the sidebar is open.
   */
  public isSidebarOpen(): boolean {
    return this.isOpen;
  }
}