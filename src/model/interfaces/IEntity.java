package model.interfaces;

import util.Direction;

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
 *
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

    // Commandes
    /**
     * L'entité attaque une autre entité.
     * @param enemy
     * @pre enemy != null
     * @post <pre>
     *     enemy.getLifePoints() == (old enemy.getL                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ifePoints()) - (enemy.getDefensePoint() / 100) * getAttackPoints()
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
}
