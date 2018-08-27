package model.enemies;

import model.interfaces.IEnemy;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class EnemyFactory {
    // STATICS
    public static IEnemy createZombie() throws URISyntaxException {
        return new ZombieEnemy(
            "Zombie",
            "Groohhrrg",
            EnemyFactory.class.getResource("../../enemy3.png").toURI(),
            2,
            0,
            5
        );
    }

    //TODO à supprimer
    public static IEnemy createZombie2() throws URISyntaxException {
        return new ZombieEnemy(
                "Zombie",
                "Groohhrrg",
                EnemyFactory.class.getResource("../../enemy4.png").toURI(),
                2,
                0,
                3
        );
    }

    public static IEnemy createZombie3() throws URISyntaxException {
        return new ZombieEnemy(
                "Zombie",
                "Groohhrrg",
                EnemyFactory.class.getResource("../../enemy2.png").toURI(),
                2,
                0,
                1
        );
    }

    // CONSTRUCTEUR
    private EnemyFactory() {}
}
