package model;

import model.interfaces.IEntity;
import model.interfaces.IItem;
import model.interfaces.IPlayer;
import model.interfaces.IRoom;
import util.Direction;

public class Player implements IPlayer {
    // ATTRIBUTS
    private int attackPoints;
    private int defensePoints;
    private int lifePoints;
    private IRoom location;
    private final String name;

    // CONSTRUCTEUR
    /**
     * Créer un nouveau joueur, avec un nom, des points d'attaque, de défense et un nombre de points de vie.
     * @param name le nom du joueur
     * @param attack ses points d'attaque
     * @param defense ses points de défense
     * @param initialLife quantité de vie initialement
     * @param room la pièce de départ
     * @pre <pre>
     *     name != null
     *     room != null
     *     attack > 0
     *     0 <= defense <= 100
     *     initialLife > 0
     * </pre>
     * @post <pre>
     *     getName().equals(name)
     *     getAttackPoints() == attack
     *     getDefensePoints() == defense
     *     getLifePoints() == initialLife
     *     getRoom() == room
     * </pre>
     */
    public Player(String name, int attack, int defense, int initialLife, IRoom room) {
        if (name == null || room == null || attack <= 0 || defense > 100 || defense < 0 || initialLife <= 0) {
            throw new AssertionError();
        }
        this.name = name;
        this.attackPoints = attack;
        this.defensePoints = defense;
        this.lifePoints = initialLife;
        this.location = room;
        room.setPlayer(this);
    }

    // REQUETES
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAttackPoints() {
        return attackPoints;
    }

    @Override
    public int getDefensivePoints() {
        return defensePoints;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public boolean isDead() {
        return getLifePoints() == 0;
    }

    @Override
    public IRoom getRoom() {
        return location;
    }

    // COMMANDES
    @Override
    public void take(IItem item) {
        this.defensePoints = Math.max(Math.min(100, this.defensePoints + item.getDefensivePoints()), 0);
        this.attackPoints = Math.max(0, this.attackPoints + item.getAttackPoints());
        this.lifePoints = Math.max(0, this.lifePoints + item.getLifePoints());
        item.take();
    }

    @Override
    public void attack(IEntity enemy) {
        if (enemy == null) {
            throw new AssertionError();
        }
        int damages = (1 - enemy.getDefensivePoints() / 100) * getAttackPoints();
        enemy.setLifePoints(enemy.getLifePoints() - damages);
    }

    @Override
    public void move(Direction direction) {
        if (direction == null || !getRoom().canExitIn(direction)) {
            throw new AssertionError();
        }
        IRoom oldRoom = getRoom();
        oldRoom.setPlayer(null);
        this.location = getRoom().getRoomIn(direction);
        getRoom().setPlayer(this);
    }

    @Override
    public void setLifePoints(int points) {
        if (points < 0) {
            throw new AssertionError();
        }
        this.lifePoints = points;
    }
}
