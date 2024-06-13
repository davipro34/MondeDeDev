import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router, NavigationEnd } from '@angular/router';
import { SidebarService } from 'src/app/services/sidebar.service';

/**
 * Represents the HeaderComponent of the application.
 */
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements AfterViewInit {
  /**
   * The title of the header.
   */
  title: string = "MDD"

  /**
   * The reference to the MatSidenav component.
   */
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private sidebarService: SidebarService) {}

  /**
   * Lifecycle hook that is called after the view has been initialized.
   */
  ngAfterViewInit():void {
    this.sidebarService.setSidenav(this.sidenav);
  }

  /**
   * Closes the sidebar.
   */
  public closeSidebar():void {
    this.sidebarService.closeSidebar();
  }

  /**
   * Checks if the sidebar is open.
   * @returns true if the sidebar is open, false otherwise.
   */
  get isOpen() {
    return this.sidebarService.isSidebarOpen();
  }
}
