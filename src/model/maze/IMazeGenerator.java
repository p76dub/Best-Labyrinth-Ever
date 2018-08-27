package model.maze;

import model.interfaces.IMaze;
import model.interfaces.IRoom;

/**
 * Interface décrivant les générateurs de la labyrinthe. Une fois le générateur créé, il suffit d'appeler la méthode
 * generate pour générer le labyrinthe à partir du tableau des pièces.
 */
public interface IMazeGenerator {
    // STATICS
    class MazeNotYetGeneratedException extends Exception {}

    // REQUÊTES
    /**
     * Obtenir la pièce désignée comme l'entrée du labyrinthe.
     * @return une pièce ou null si le labyrinthe n'a pas été généré
     */
    IRoom getEntry() throws MazeNotYetGeneratedException;

    /**
     * Obtenir la pièce désignée comme la sortie du labyrinthe.
     * @return une pièce ou null si le labyrinthe n'a pas été généré
     */
    IRoom getExit() throws MazeNotYetGeneratedException;

    /**
     * Obtenir la pièce désignée comme contenant la princesse.
     * @return une pièce ou null si le labyrinthe n'a pas été généré ou si le générateur ne place pas de princesse.
     */
    IRoom getPrincessRoom() throws MazeNotYetGeneratedException;

    /**
     * Obtenir le labyrinthe créé par ce générateur. Attention, si cette méthode est appelée avant la méthode generate,
     * le labyrinthe n'aura que des pièces sans connexions.
     */
    IMaze getMaze();

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
