import { Component, OnDestroy, OnInit } from '@angular/core';
import { Article } from '../../interfaces/article';
import { Subscription, switchMap } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/features/auth/services/session.service';

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

  changeSortOrder(sortBy: string, direction: string): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { sort: sortBy, direction: direction },
      queryParamsHandling: 'merge'
    }).then(() => {
      this.articleService.sortArticles(sortBy, direction);
    });
  }

  onSortClick(sortBy: string, direction: string): void {
    this.sortDirection = direction;
    this.sortDirections[sortBy] = direction === 'asc' ? 'desc' : 'asc';
    this.changeSortOrder(sortBy, this.sortDirection);
  }

  ngOnDestroy(): void {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
