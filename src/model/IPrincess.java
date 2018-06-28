package model;

public interface IPrincess {

    // Requêtes
    /**
     * Retourne le nom de l'image de l'ennemi
     * @return nameImage
     */
    String getNameImage();

    /**
     * Retourne si la princesse a été retrouvée
     * @return save
     */
    boolean isSave();

    /**
     * Retourne le message d'apparition de l'item
     * @return message
     */
    String getMessage();

    /**
     * Retourne la pièce où se situe le joueur
     * @return myRoom
     */
    IRoom getRoom();

    // Commande
    /**
     * Trouve la princesse et la sauve
     */
    void save();
}