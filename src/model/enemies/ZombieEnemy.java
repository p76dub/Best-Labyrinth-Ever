package model.enemies;

import model.enemies.behaviours.RandomMoveBehaviour;

import java.net.URI;
import java.net.URISyntaxException;

public class ZombieEnemy extends Enemy {
    public ZombieEnemy(int attack, int defense, int life) throws URISyntaxException {
        super(
                "Zombie",
                "Grrrhorrrg",
                ZombieEnemy.class.getResource("../../enemy3.png").toURI(),
                attack,
                defense,
                life
        );
    }

    @Override
    public void initialization() {
        addBehaviour(new RandomMoveBehaviour(this));
    }
}
