package model.interfaces;

import java.net.URI;

/**
 * Interface décrivant les possibilités des ennemis. En plus des méthodes déclarées dans l'interface IEntity, les
 * ennemis possèdent unmessage, affiché lorsqu'un joueur les rencontre, et un chemin vers une image.
 * @inv <pre>
 *     getName() != null
 * </pre>
 */
public interface IEnemy extends IEntity {

    // Requêtes
    /**
     * Retourne le chemin vers l'image de l'ennemi.
     * @return nameImage
     */
    URI getImagePath();

    /**
     * Retourne le message affiché lorsque l'ennemy rencontre le joueur.
     * @return message
     */
    String getMessage();
}