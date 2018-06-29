package model;

import util.Direction;

public interface IEntity {

    // Requêtes
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
     * @post enemy.getLifePoints() == enemy.getLifePoints() - DEGAT
     */
    void attack(IEntity enemy);

    /**
     * Déplace l'entité en fonction de la direction
     * @param direction
     * @pre direction != null && getRoom().canExitIn(direction)
     * @post getRoom() == getRoom().getRoomIn(direction)
     */
    void move(Direction direction);
}
