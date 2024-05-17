/**
 * Représente le succès de la connexion.
 */
export interface LoginSuccess {
  /**
   * Le jeton d'authentification.
   * 
   * @param {string} token - Le jeton d'authentification.
   */
  token: string;
}
