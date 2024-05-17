/**
 * Représente les informations d'inscription d'un utilisateur.
 */
export interface Register {
  /**
   * Le nom d'utilisateur de l'utilisateur.
   * @param {string} username - Le nom d'utilisateur.
   */
  username: string;

  /**
   * L'adresse e-mail de l'utilisateur.
   * @param {string} email - L'adresse e-mail.
   */
  email: string;

  /**
   * Le mot de passe de l'utilisateur.
   * @param {string} password - Le mot de passe.
   */
  password: string;
}
