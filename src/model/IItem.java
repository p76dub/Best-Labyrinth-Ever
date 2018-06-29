package model;

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
    int getAttackPoints();

    /**
     * Retourne le nombre de points de défense de l'item
     * @return defensivePoints
     */
    int getDefensivePoints();

    /**
     * Retourne si l'item a été ramassé.
     * @return taken
     */
    boolean isTaken();

    /**
     * Retourne la pièce dans laquelle se trouve l'item
     * @return myRoom
     * @pre !isTaken()
     */
    IRoom getRoom();

    /**
     * Retourne le message d'apparition de l'item.
     * @return message
     */
    abstract String getMessage();

    // Commandes
    /**
     * Prendre l'item.
     * @post isTaken()
     */
    void take();
}