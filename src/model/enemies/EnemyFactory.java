package model.enemies;

import model.interfaces.IEnemy;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class EnemyFactory {
    // STATICS
    public static IEnemy createZombie() throws URISyntaxException {
        return new Enemy(
                "Zombie",
                "Groohhrrg",
                EnemyFactory.class.getResource("../../../images/zombie.png").toURI(),
                2,
                0,
                5,
                new ArrayList<>()
        );
    }

    // CONSTRUCTEUR
    private EnemyFactory() {}
}
