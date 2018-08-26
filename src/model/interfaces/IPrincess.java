package model.interfaces;

import java.beans.PropertyChangeListener;
import java.net.URI;

public interface IPrincess {

    // STATICS
    String SAFE_PROPERTY = "save";

    // Requêtes
    /**
     * Retourne le chemin de l'image de la princesse.
     * @return nameImage
     */
    URI getImagePath();

    /**
     * Retourne si la princesse a été retrouvée
     * @return safe
     */
    boolean isSafe();

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

    void addPropertyChangeListener(String property, PropertyChangeListener l);
    void removePropertyChangeListener(PropertyChangeListener l);

}