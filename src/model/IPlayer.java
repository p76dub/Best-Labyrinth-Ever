package model;

import util.Direction;

public interface IPlayer extends IEntity {
    /**
     * Modifie les points d'attaque de le joueur
     * @param points
     * @post getAttackPoints() == points
     */
    void setAttackPoints(int points);

    /**
     * Modifie les points de défense de le joueur
     * @param points
     * @post getDefensivePoints() == points
     */
    void setDefensivePoints(int points);

    /**
     * Modifie les points de vie de le joueur
     * @param points
     * @post getLifePoints() == points
     */
    void setLifePoints(int points);

    /**
     * Equiper un item.
     * @param item
     */
    void equipe(IItem item);
}