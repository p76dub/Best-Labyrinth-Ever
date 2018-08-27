package model.interfaces;

import util.Direction;

/**
 * Le labyrinthe des pièces, rectangulaire (rows * cols).
 * @inv <pre>
 *     entry() != null
 *     exit() != null
 *     </pre>
 */
public interface IMaze {

    class MazeGeneratorCreationException extends Exception {
        public MazeGeneratorCreationException(String msg) {
            super(msg);
        }

        public MazeGeneratorCreationException(Throwable cause) {
            super(cause);
        }
    }

    int DEFAULT_WIDTH = 15;
    int DEFAULT_HEIGHT = 15;

    // Requêtes
    /**
     * Retourne le nombre de lignes du labyrinthe
     * @return rows
     */
    int rowsNb();

    /**
     * Retourne le nombre de colonnes du labyrinthe
     * @return cols
     */
    int colsNb();

    /**
     * L'entrée du labyrinthe.
     */
    IRoom entry();

    /**
     * La sortie du labyrinthe.
     */
    IRoom exit();

    /**
     * Retourne les pièces du labyrinthe.
     * @return rooms
     */
    IRoom[][] getRooms();

    /**
     * Retourne la princesse du labyrinthe.
     * @return la princesse
     */
    IPrincess getPrincess();

    INetwork<IRoom, Direction> getNetwork();

    /**
     * Marque la pièce r comme visitée (utilisé lors de l'affichage du
     *  labyrinthe).
     */
    void mark(IRoom r);
}