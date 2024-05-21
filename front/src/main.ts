/**
 * Fichier principal de l'application.
 */

import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

/**
 * Active le mode de production si l'environnement est en production.
 */
if (environment.production) {
  enableProdMode();
}

/**
 * Démarre l'application en chargeant le module AppModule.
 * @throws {any} Erreur lors du démarrage de l'application.
 */
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
