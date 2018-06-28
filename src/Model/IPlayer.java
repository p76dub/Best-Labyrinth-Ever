package Model;

public interface IPlayer {

    // Requêtes
    /**
     * Retourne le nombre de points d'attaque de le joueur
     * @return attackPoints
     */
    Int getAttackPoints();

    /**
     * Retourne le nombre de points de défense de le joueur
     * @return defensivePoints
     */
    Int getDefensivePoints();

    /**
     * Retourne le nombre de points de vie de le joueur
     * @return lifePoints
     */
    Int getLifePoints();

    /**
     * Retourne si le joueur est mort
     * @return getLifePoints() == 0
     */
    boolean isDead();

    /**
     * Retourne la pièce où se situe le joueur
     * @return myRoom
     */
    IRoom getRoom();

    // Commandes
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
     * Le joueur attaque!
     * @param enemy
     * @pre enemy != null
     * @post enemy.getLifePoints() == enemy.getLifePoints() - DEGAT
     */
    void attack(IEnemy enemy);

    /**
     * Le joueur se défend!
     * @param enemy
     * @pre enemy != null
     * @post getLifePoints() == getLifePoints() - DEGAT
     */
    void defense(IEnemy enemy);

    /**
     * Déplace le joueur en fonction de la direction
     * @param direction
     * @pre direction != null && getRoom().canExitIn(direction)
     * @post getRoom() == getRoom().getRoomIn(direction)
     */
    void advance(Direction direction);
}