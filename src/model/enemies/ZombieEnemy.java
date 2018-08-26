package model.enemies;

import model.enemies.behaviours.RandomMoveBehaviour;

import java.net.URI;

public class ZombieEnemy extends Enemy {
    public ZombieEnemy(String name, String message, URI imagePath, int attack, int defense, int life) {
        super(name, message, imagePath, attack, defense, life);
    }

    @Override
    public void initialization() {
        addBehaviour(new RandomMoveBehaviour(this));
    }
}
