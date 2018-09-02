package model.enemies;

import model.interfaces.IEnemy;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class EnemyFactory {
    // STATICS
    public static final int SIMPLE_ZOMBIE = 0;
    public static final int ADVANCED_ZOMBIE = 1;

    public static IEnemy createAdvancedZombie() throws URISyntaxException {
        return new ZombieEnemy(2,0,5);
    }

    //TODO à supprimer
    public static IEnemy createSimpleZombie() throws URISyntaxException {
        return new ZombieEnemy(2, 0, 3);
    }

    public static IEnemy createEnemy(int enemy) throws URISyntaxException {
        switch (enemy) {
            case SIMPLE_ZOMBIE:
                return createSimpleZombie();
            case ADVANCED_ZOMBIE:
                return createAdvancedZombie();
            default:
                throw new AssertionError();
        }
    }

    // CONSTRUCTEUR
    private EnemyFactory() {}
}
