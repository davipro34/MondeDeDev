import { Component, OnDestroy, OnInit } from '@angular/core';
import { Article } from '../../interfaces/article';
import { Subscription, switchMap } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/features/auth/services/session.service';

/**
 * Represents the ListComponent class.
 * This component is responsible for displaying a list of articles and handling sorting functionality.
 */
@Component({
  selector: 'app-list',
  standalone: false,
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent implements OnInit, OnDestroy {
  sortBy: string | null = null;
  sortDirection: string | null = null;
  articles: Article[] = [];
  sortDirections: { [key: string]: string } = {
    date: 'asc',
    title: 'asc',
  };

  private articleSubscription: Subscription | null = null;

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router,
    private sessionService: SessionService
  ) { }

  /**
   * Initializes the component.
   * Subscribes to route query parameters to get the sorting options.
   * Subscribes to the articleService to get the articles for the subscribed themes.
   */
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.sortBy = params['sort'];
      this.sortDirection = params['direction'];
      if (!this.sortBy || !this.sortDirection) {
        this.changeSortOrder('date', 'desc');
      }
    });

    this.articleSubscription = this.sessionService.subscribedThemes$.pipe(
      switchMap(themes => this.articleService.getArticlesForThemes(themes.map(theme => theme.id))),
      switchMap(() => this.articleService.articles$)
    ).subscribe(articles => {
      this.articles = articles;
    });
  }

  /**
   * Changes the sort order of the articles.
   * Navigates to the current route with updated query parameters for sorting.
   * Calls the articleService to sort the articles.
   * @param sortBy - The field to sort by.
   * @param direction - The sort direction.
   */
  changeSortOrder(sortBy: string, direction: string): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { sort: sortBy, direction: direction },
      queryParamsHandling: 'merge'
    }).then(() => {
      this.articleService.sortArticles(sortBy, direction);
    });
  }

  /**
   * Handles the click event on the sort button.
   * Updates the sort direction and changes the sort order.
   * @param sortBy - The field to sort by.
   * @param direction - The current sort direction.
   */
  onSortClick(sortBy: string, direction: string): void {
    this.sortDirection = direction;
    this.sortDirections[sortBy] = direction === 'asc' ? 'desc' : 'asc';
    this.changeSortOrder(sortBy, this.sortDirection);
  }

  /**
   * Cleans up the component.
   * Unsubscribes from the articleSubscription if it exists.
   */
  ngOnDestroy(): void {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
