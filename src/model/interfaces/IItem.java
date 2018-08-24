package model.interfaces;

import java.nio.file.Path;

public interface IItem {

    //Constantes
    int MAX_ITEM_ATTACK_POINTS = 5;
    int MIN_ITEM_ATTACK_POINTS = -5;
    int MAX_ITEM_DEFENSIVE_POINTS = 5;
    int MIN_ITEM_DEFENSIVE_POINTS = -5;
    int MAX_ITEM_LIVE_POINTS = 5;
    int MIN_ITEM_LIVE_POINTS = -5;

    // Requêtes
    /**
     * Retourne le nom de l'image de l'item
     * @return nameImage
     */
    Path getImagePath();

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
     * Retourne le nombre de points de vie de l'item
     * @return lifePoints
     */
    int getLifePoints();

    /**
     * Retourne si l'item a été ramassé.
     * @return taken
     */
    boolean isTaken();

    /**
     * Retourne le message d'apparition de l'item.
     * @return message
     */
    String getMessage();

    /**
     * Prendre l'item.
     * @post isTaken()
     */
    void take();
}