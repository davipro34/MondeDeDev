/**
 * Represents the form component for creating articles.
 */
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Theme } from 'src/app/features/themes/interfaces/theme';
import { ThemeService } from 'src/app/features/themes/services/theme.service';
import { ArticleService } from '../../services/article.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Article } from '../../interfaces/article';

@Component({
  selector: 'app-form',
  standalone: false,
  templateUrl: './form.component.html',
  styleUrl: './form.component.scss'
})
export class FormComponent implements OnInit, OnDestroy {
  themes: Theme[] = [];

  /**
   * Represents the form controls for the article form.
   */
  formControls: { [key: string]: FormControl } = {
    theme: new FormControl('', [Validators.required]),
    title: new FormControl('', [Validators.required]),
    content: new FormControl('', [Validators.required])
  };

  /**
   * Represents the labels for the form controls.
   */
  labels: { [key: string]: string } = {
    theme: 'Sélectionner un thème',
    title: 'Titre de l’article',
    content: 'Contenu de l’article'
  };

  /**
   * Represents the names of the form controls.
   */
  controlNames: { [key: string]: string } = {
    theme: 'un thème',
    title: 'un titre',
    content: 'du contenu'
  };

  /**
   * Represents the error messages for the form controls.
   */
  errorMessages: { [key: string]: string } = {
    theme: '',
    title: '',
    content: ''
  };

  private themeSubscription: Subscription | null = null;
  private articleSubscription: Subscription | null = null;

  constructor(private themeService: ThemeService, private articleService: ArticleService, private snackBar: MatSnackBar) {}

  /**
   * Initializes the component by subscribing to the theme service to get the themes.
   */
  ngOnInit(): void {
    this.themeSubscription = this.themeService.getThemes().subscribe(themes => {
      this.themes = themes;
      console.log("themes:", themes); //TODO remove log
    });
  }

  /**
   * Handles the blur event of a form control.
   * @param controlName - The name of the form control.
   */
  onBlur(controlName: string):void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  /**
   * Handles the form submission.
   */
  onSubmit(): void {
    if (this.formControls['theme'].valid && this.formControls['title'].valid && this.formControls['content'].valid) {
      const newArticle: Pick<Article, 'title' | 'content' | 'themeId'> = {
        themeId: this.formControls['theme'].value,
        title: this.formControls['title'].value,
        content: this.formControls['content'].value
      };

      this.articleSubscription = this.articleService.createArticle(newArticle).subscribe({
        next: () => {
          this.snackBar.open('Article créé avec succès', 'Fermer', {
            duration: 3000,
          });
          Object.values(this.formControls).forEach(control => {
            control.reset();
            control.setErrors(null);
          });
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
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
