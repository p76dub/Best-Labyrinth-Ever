package model.interfaces;

import util.Direction;

import java.beans.PropertyChangeListener;
import java.net.URI;

/**
 * Les entités sont des objets qui sont capables de se déplacer dans le labyrinthe. Elles sont également capables de se
 * battre, et possèdent donc des points de vies, des points de défense et des points d'attaque.
 * @inv <pre>
 *     getName() != null
 *     getAttackPoint() > 0
 *     100 >= getDefensivePoints() >= 0
 *     getLifePoints() >= 0
 *     isDead() <-> getLifePoints() == 0
 *     getRoom() != null
 *     getOrientation() != null
 * </pre>
 */
public interface IEntity {

    // Requêtes
    /**
     * Le nom de l'entité.
     * @return un nom
     */
    String getName();

    /**
     * Retourne le nombre de points d'attaque de l'entité
     * @return attackPoints
     */
    int getAttackPoints();

    /**
     * Retourne le nombre de points de défense de l'entité (points d'armure)
     * @return defensivePoints
     */
    int getDefensivePoints();

    /**
     * Retourne le nombre de points de vie de l'entité
     * @return lifePoints
     */
    int getLifePoints();

    /**
     * Retourne si le joueur est mort
     * @return getLifePoints() == 0
     */
    boolean isDead();

    /**
     * Retourne la pièce dans laquelle se situe l'entité.
     * @return myRoom
     */
    IRoom getRoom();

    /**
     * Obtenir l'orientation du joueur.
     */
    Direction getOrientation();

    /**
     * Retourne le chemin vers l'image (celle sur le labyrinthe).
     * @return nameImage
     */
    URI getMazeImagePath();

    // Commandes
    /**
     * L'entité attaque une autre entité.
     * @param enemy
     * @pre enemy != null
     * @post <pre>
     *     enemy.getLifePoints() == (old enemy.getLifePoints()) -                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ifePoints()) - (enemy.getDefensePoint() / 100) * getAttackPoints()
     * </pre>
     */
    void attack(IEntity enemy);

    /**
     * Déplace l'entité en fonction de la direction
     * @param direction
     * @pre direction != null && getRoom().canExitIn(direction)
     * @post getRoom() == getRoom().getRoomIn(direction)
     */
    void move(Direction direction);

    /**
     * Modifie les points de vie de l'entité
     * @param points
     * @pre points >= 0
     * @post getLifePoints() == points
     */
    void setLifePoints(int points);

    /**
     * Modifier l'orientation du joueur.
     * @param d la direction dans laquelle est tourné le joueur.
     * @pre d != null
     * @post getOrientation().equals(d);
     */
    void setOrientation(Direction d);

    void addPropertyChangeListener(String property, PropertyChangeListener l);
    void removePropertyChangeListener(PropertyChangeListener l);
}
