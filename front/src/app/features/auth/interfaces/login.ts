/**
 * Représente les informations de connexion.
 */
export interface Login {
  /**
   * L'email ou le nom d'utilisateur de l'utilisateur.
   * @param {string} usernameOrEmail - L'email ou le nom d'utilisateur.
   */
  usernameOrEmail: string;

  /**
   * Le mot de passe de l'utilisateur.
   * @param {string} password - Le mot de passe.
   */
  password: string;
}
