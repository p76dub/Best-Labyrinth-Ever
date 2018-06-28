package Model;

public interface IItem {

    // Requêtes
    /**
     * Retourne le nom de l'image de l'item
     * @return nameImage
     */
    String getNameImage();

    /**
     * Retourne le nombre de points d'attaque de l'item
     * @return attackPoints
     */
    Int getAttackPoints();

    /**
     * Retourne le nombre de points de défense de l'item
     * @return defensivePoints
     */
    Int getDefensivePoints();

    /**
     * Retourne si l'item a été ramassé
     * @return taken
     */
    boolean hasTaken();

    /**
     * Retourne la pièce où se situe l'ennemi
     * @return myRoom
     */
    IRoom getRoom();

    /**
     * Retourne le message d'apparition de l'item
     * @return message
     */
    abstract String getMessage();

    // Commandes
    /**
     * Modifie les points d'attaque de l'item
     * @param points
     * @post getAttackPoints() == points
     */
    void setAttackPoints(int points);

    /**
     * Modifie les points de défense de l'item
     * @param points
     * @post getDefensivePoints() == points
     */
    void setDefensivePoints(int points);

    /**
     * Prends l'item.
     * @post getTaken()
     */
    void setTaken();
}