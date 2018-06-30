package model.interfaces;

import java.util.List;

public interface ITheme {

    // Requêtes
    /**
     * Retourne le nom du thème
     * @return name
     */
    String getName();

    /**
     * Retourne la liste des ennemis possibles
     * @return enemies
     */
    List getEnemy();

    /**
     * Retourne le labyrinthe associé au thème
     * @return labyrinth
     */
    ILabyrinth getLabyrinth();
}