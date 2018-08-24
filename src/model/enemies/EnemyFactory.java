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
            EnemyFactory.class.getResource("../../epee.png").toURI(),
            2,
            0,
            5
        );
    }

    // CONSTRUCTEUR
    private EnemyFactory() {}
}
