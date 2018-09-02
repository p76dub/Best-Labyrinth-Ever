package model;

import model.enemies.EnemyFactory;

import java.net.URI;
import java.net.URISyntaxException;

public enum Theme {
    /*HARRY_POTTER(
            "Harry Potter",
            "../harry_potter.png",
            new int[]{}
    ),
    POKEMON(
            "Pokémon",
            "../pokemon.png",
            new int[]{}
    ),
    STAR_WARS(
            "Star Wars",
            "../star_wars.png",
            new int[]{}
    ),
    LORD_OF_THE_RING(
            "Le seigneur des anneaux",
            "../lord_of_the_ring.png",
            new int[]{}
    ),
    GAMES_OF_THRONE(
            "Games of Throne",
            "../got.png",
            new int[]{}
    ),*/
    APOCALYPSE(
            "Apocalypse",
            "../zombie.png",
            new int[]{EnemyFactory.SIMPLE_ZOMBIE, EnemyFactory.ADVANCED_ZOMBIE}
    );/*,
    EMPTY(
            "Sans Thème",
            "../empty.png",
            new int[]{}
    );*/

    // ATTRIBUTS
    private final String name;
    private final URI picture;
    private final int[] enemies;

    // CONSTRUCTOR
    Theme(String name, String pictureLocation, int[] enemies) {
        if (name == null || pictureLocation == null || enemies == null) {
            throw new NullPointerException();
        }
        this.name = name;
        try {
            this.picture = this.getClass().getResource(pictureLocation).toURI();
        } catch (URISyntaxException e) {
            throw new AssertionError();
        }
        this.enemies = enemies;
    }

    public String getName() {
        return name;
    }

    public URI getPicture() {
        return picture;
    }

    public int[] getEnemies() {
        return enemies;
    }

    @Override
    public String toString() {
        return getName();
    }
}
