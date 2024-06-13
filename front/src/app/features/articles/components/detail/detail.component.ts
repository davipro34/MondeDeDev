import { Component, OnDestroy, OnInit } from '@angular/core';
import { Article } from '../../interfaces/article';
import { Comment } from "../../interfaces/comment";
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../services/article.service';

@Component({
  selector: 'app-detail',
  standalone: false,
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.scss'
})
/**
 * Represents the detail component for displaying an article and its comments.
 */
export class DetailComponent implements OnInit, OnDestroy {
  article: Article | undefined;
  comments: Comment[] = [];
  formControls: { [key: string]: FormControl } = {
    content: new FormControl('', [Validators.required])
  };

  labels: { [key: string]: string } = {
    content: 'Écrivez ici votre commentaire'
  };

  controlNames: { [key: string]: string } = {
    content: 'du contenu'
  };

  errorMessages: { [key: string]: string } = {
    content: ''
  };

  private routeSubscription: Subscription | null = null;
  private articleSubscription: Subscription | null = null;
  private createCommentSubscription: Subscription | null = null;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
  ) {}

  /**
   * Initializes the component by subscribing to route parameters and fetching the article and its comments.
   */
  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      const id = +params['id'];
      this.articleSubscription = this.articleService.getArticle(id).subscribe(article => {
        this.article = article;
        this.articleService.getComments(id).subscribe(comments => {
          this.comments = comments;
        });
      });
    });
  }

  /**
   * Handles the blur event of a form control.
   * @param controlName - The name of the form control.
   */
  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  /**
   * Handles the submission of a comment form.
   */
  onSubmitComment(): void {
    if (this.formControls['content'].valid && this.article?.id) {
      const newComment: Pick<Comment, 'articleId' | 'content'> = {
        content: this.formControls['content'].value,
        articleId: this.article.id,
      };

      this.createCommentSubscription = this.articleService.createComment(newComment).subscribe({
        next: createdComment => {
          this.comments.push(createdComment);
          this.formControls['content'].reset();
          this.formControls['content'].setErrors(null);
        },
        error: error => {
          throw error;
        }
      });
    }
  }

  /**
   * Cleans up subscriptions when the component is destroyed.
   */
  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }

    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }

    if (this.createCommentSubscription) {
      this.createCommentSubscription.unsubscribe();
    }
  }
}