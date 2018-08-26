package model.enemies;

import model.EntityPositionKeeper;
import model.interfaces.IEnemy;
import model.interfaces.IEntity;
import model.interfaces.IRoom;
import util.Direction;
import util.agent.Agent;

import java.net.URI;


class Enemy extends Agent implements IEnemy {
    // ATTRIBUTS
    private final URI imagePath;
    private final String message;
    private final int attack;
    private final int defense;
    private int life;
    private Direction orientation;

    // CONSTRUCTEUR
    public Enemy(String name, String message, URI imagePath, int attack, int defense, int life) {
        super(name);
        if (message == null || imagePath == null) {
            throw new NullPointerException();
        }
        if (0 >= attack || 0 > defense || defense > 100 || life <= 0) {
            throw new IllegalArgumentException();
        }
        this.message = message;
        this.imagePath = imagePath;
        this.attack = attack;
        this.defense = defense;
        this.life = life;
        this.orientation = Direction.EAST;
    }

    @Override
    public URI getMazeImagePath() {
        return this.imagePath;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getAttackPoints() {
        return this.attack;
    }

    @Override
    public int getDefensivePoints() {
        return this.defense;
    }

    @Override
    public int getLifePoints() {
        return this.life;
    }

    @Override
    public boolean isDead() {
        return this.life <= 0;
    }

    @Override
    public IRoom getRoom() {
        return EntityPositionKeeper.getInstance().getPosition(this);
    }

    @Override
    public Direction getOrientation() {
        return this.orientation;
    }

    @Override
    public void attack(IEntity enemy) {
        if (enemy == null) {
            throw new AssertionError();
        }
        int damages = (1 - enemy.getDefensivePoints() / 100) * getAttackPoints();
        enemy.setLifePoints(Math.max(0, enemy.getLifePoints() - damages));
    }

    @Override
    public void move(Direction direction) {
        if (getRoom() == null) {
            throw new NullPointerException();
        }
        if (!getRoom().canExitIn(direction)) {
            throw new AssertionError();
        }
        setOrientation(direction);
        EntityPositionKeeper.getInstance().move(this, getRoom().getRoomIn(direction));
    }

    @Override
    public void setLifePoints(int points) {
        if (points < 0) {
            throw new AssertionError();
        }
        this.life = points;
    }

    @Override
    public void setOrientation(Direction d) {
        if (d == null) {
            throw new NullPointerException();
        }
        this.orientation = d;
    }
}
