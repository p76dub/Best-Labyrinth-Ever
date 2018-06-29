package model;

import util.Direction;

import java.nio.file.Path;

public interface IEnemy extends IEntity {

    // Requêtes
    /**
     * Retourne le chemin vers l'image de l'ennemi.
     * @return nameImage
     */
    Path getImagePath();

    /**
     * Retourne le message affiché lorsque l'ennemy rencontre le joueur.
     * @return
     */
    String getMessage();
}