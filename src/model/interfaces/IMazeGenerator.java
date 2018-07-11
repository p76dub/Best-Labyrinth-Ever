package model.interfaces;

/**
 * Interface décrivant les générateurs de la labyrinthe. Une fois le générateur créé, il suffit d'appeler la méthode
 * generate pour générer le labyrinthe à partir du tableau des pièces.
 */
public interface IMazeGenerator {
    // REQUÊTES
    /**
     * Obtenir la pièce désignée comme l'entrée du labyrinthe.
     * @return une pièce ou null si le labyrinthe n'a pas été généré
     */
    IRoom getEntry();

    /**
     * Obtenir la pièce désignée comme la sortie du labyrinthe.
     * @return une pièce ou null si le labyrinthe n'a pas été généré
     */
    IRoom getExit();

    /**
     * Obtenir la pièce désignée comme contenant la princesse.
     * @return une pièce ou null si le labyrinthe n'a pas été généré ou si le générateur ne place pas de princesse.
     */
    IRoom getPrincessRoom();

    // COMMANDE
    /**
     * Générer le labyrinthe.
     * @post <pre>
     *     getEntry() != null
     *     getExit() != null
     * </pre>
     */
    void generate();
}
