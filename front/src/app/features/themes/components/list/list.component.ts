/**
 * Represents the ListComponent class.
 * This component is responsible for displaying a list of themes and handling theme subscriptions.
 */
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Theme } from '../../interfaces/theme';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../services/theme.service';
import { SessionService } from 'src/app/features/auth/services/session.service';
import { UserService } from 'src/app/features/me/services/user.service';

@Component({
  selector: 'app-themes',
  standalone: false,
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent implements OnInit, OnDestroy {
  public themes: Theme[] = [];
  public subscribedThemeIds: number[] = [];
  private themesSubscription: Subscription | null = null;
  private userServiceSubscription: Subscription | null = null;

  constructor(private themeService: ThemeService,
              private sessionService: SessionService,
              private userService: UserService) {}

  /**
   * Initializes the component.
   * Subscribes to the themeService to get all themes and the sessionService to get subscribed themes.
   */
  ngOnInit(): void {
    this.themesSubscription = this.themeService.getThemes().subscribe(allThemes => {
      this.themes = allThemes;
    });

    this.sessionService.subscribedThemes$.subscribe(themes => {
      this.subscribedThemeIds = themes.map(theme => theme.id);
    });
  }

  /**
   * Checks if a theme is subscribed.
   * @param themeId - The ID of the theme to check.
   * @returns True if the theme is subscribed, false otherwise.
   */
  public isSubscribed(themeId: number): boolean {
    return this.subscribedThemeIds.includes(themeId);
  }

  /**
   * Subscribes to a theme.
   * @param themeId - The ID of the theme to subscribe to.
   */
  public onSubscribe(themeId: number): void {
    this.userServiceSubscription = this.userService.subscribeToTheme(themeId).subscribe((updatedUser) => {
      this.sessionService.updateUser(updatedUser);
    });
  }

  /**
   * Cleans up subscriptions when the component is destroyed.
   */
  ngOnDestroy(): void {
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
    if (this.userServiceSubscription) {
      this.userServiceSubscription.unsubscribe();
    }
  }
}
