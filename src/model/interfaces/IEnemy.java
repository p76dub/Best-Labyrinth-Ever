package model.interfaces;

import util.agent.IAgent;

import java.net.URI;

/**
 * Interface décrivant les possibilités des ennemis. En plus des méthodes déclarées dans l'interface IEntity, les
 * ennemis possèdent unmessage, affiché lorsqu'un joueur les rencontre, et un chemin vers une image.
 * @inv <pre>
 *     getName() != null
 * </pre>
 */
public interface IEnemy extends IEntity, IAgent {
    // STATICS
    String POSITION_PROPERTY = "position";

    // Requêtes
    /**
     * Retourne le message affiché lorsque l'ennemy rencontre le joueur.
     * @return message
     */
    String getMessage();
}