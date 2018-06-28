package Model;

public interface IEnemy {

    // Requêtes
    /**
     * Retourne le nom de l'image de l'ennemi
     * @return nameImage
     */
    String getNameImage();

    /**
     * Retourne le nombre de points d'attaque de l'ennemi
     * @return attackPoints
     */
    Int getAttackPoints();

    /**
     * Retourne le nombre de points de défense de l'ennemi
     * @return defensivePoints
     */
    Int getDefensivePoints();

    /**
     * Retourne le nombre de points de vie de l'ennemi
     * @return lifePoints
     */
    Int getLifePoints();

    /**
     * Retourne le message d'apparition de l'ennemi
     * @return message
     */
    String getMessage();

    /**
     * Retourne si l'ennemi est mort
     * @return getLifePoints() == 0
     */
    boolean isDead();

    /**
     * Retourne la pièce où se situe l'ennemi
     * @return myRoom
     */
    IRoom getRoom();

    // Commandes
    /**
     * Modifie les points d'attaque de l'ennemi
     * @param points
     * @post getAttackPoints() == points
     */
    void setAttackPoints(int points);

    /**
     * Modifie les points de défense de l'ennemi
     * @param points
     * @post getDefensivePoints() == points
     */
    void setDefensivePoints(int points);

    /**
     * Modifie les points de vie de l'ennemi
     * @param points
     * @post getLifePoints() == points
     */
    void setLifePoints(int points);

    /**
     * L'ennemi attaque!
     * @param player
     * @pre player != null
     * @post player.getLifePoints() == player.getLifePoints() - DEGAT
     */
    void attack(IPlayer player);

    /**
     * L'ennemi se défend!
     * @param player
     * @pre player != null
     * @post getLifePoints() == getLifePoints() - DEGAT
     */
    void defense(IPlayer player);

    /**
     * Déplace l'ennemi en fonction de la direction
     * @param direction
     * @pre direction != null && getRoom().canExitIn(direction)
     * @post getRoom() == getRoom().getRoomIn(direction)
     */
    void advance(Direction direction);
}