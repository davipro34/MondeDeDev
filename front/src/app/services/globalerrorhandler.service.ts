import { Injectable, ErrorHandler } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

/**
 * Service that handles global errors and displays error messages using MatSnackBar.
 */
@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  /**
   * Constructs a new instance of the GlobalErrorHandler service.
   * @param snackBar - The MatSnackBar service used to display error messages.
   */
  constructor(private snackBar: MatSnackBar) { }

  /**
   * Handles the error and displays an appropriate error message.
   * @param error - The error object to handle.
   */
  handleError(error: Error | HttpErrorResponse) {
    let message: string;

    if (error instanceof HttpErrorResponse) {
      if (error.status === 401) {
        message = 'Veuillez vous connecter';
      } else {
        message = 'Une erreur s\'est produite : ' + error.message;
      }
    } else {
      message = 'Une erreur s\'est produite : ' + error.message;
    }

    this.snackBar.open(message, 'Fermer', {
      duration: 4000,
      horizontalPosition: 'center',
    });
  }
}