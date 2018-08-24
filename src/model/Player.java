package model;

import model.interfaces.IEntity;
import model.interfaces.IItem;
import model.interfaces.IPlayer;
import model.interfaces.IRoom;
import util.Direction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Player implements IPlayer {
    // STATICS
    public static final String POSITION = "position";
    public static final String DEFENSE = "defense";
    public static final String ATTACK = "attack";
    public static final String LIFE = "life";

    // ATTRIBUTS
    private int attackPoints;
    private int defensePoints;
    private int lifePoints;
    private final String name;
    private PropertyChangeSupport propertySupport;
    private Direction orientation;
    private final Map<Direction, URI> images;

    // CONSTRUCTEUR
    /**
     * Créer un nouveau joueur, avec un nom, des points d'attaque, de défense et un nombre de points de vie. Le joueur
     * est initialement tourné vers la droite.
     * @param name le nom du joueur
     * @param attack ses points d'attaque
     * @param defense ses points de défense
     * @param initialLife quantité de vie initialement
     * @pre <pre>
     *     name != null
     *     attack > 0
     *     0 <= defense <= 100
     *     initialLife > 0
     * </pre>
     * @post <pre>
     *     getName().equals(name)
     *     getAttackPoints() == attack
     *     getDefensePoints() == defense
     *     getLifePoints() == initialLife
     *     getOrientation().equals(Direction.EAST)
     * </pre>
     */
    public Player(String name, int attack, int defense, int initialLife, Map<Direction, URI> images) {
        if (name == null || attack <= 0 || defense > 100 || defense < 0 || initialLife <= 0) {
            throw new AssertionError();
        }
        this.name = name;
        this.attackPoints = attack;
        this.defensePoints = defense;
        this.lifePoints = initialLife;
        propertySupport = new PropertyChangeSupport(this);
        this.orientation = Direction.EAST;
        this.images = new HashMap<>(images);
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
        return EntityPositionKeeper.getInstance().getPosition(this);
    }

    @Override
    public Direction getOrientation() {
        return orientation;
    }

    @Override
    public URI getMazeImagePath() {
        return images.get(getOrientation());
    }

    // COMMANDES
    @Override
    public void take(IItem item) {
        int oldDefensivePoints = getDefensivePoints();
        int oldAttackPoints = getAttackPoints();
        int oldLifePoints = getLifePoints();

        this.defensePoints = Math.max(Math.min(100, this.defensePoints + item.getDefensivePoints()), 0);
        this.attackPoints = Math.max(0, this.attackPoints + item.getAttackPoints());
        this.lifePoints = Math.max(0, this.lifePoints + item.getLifePoints());
        item.take();

        propertySupport.firePropertyChange(DEFENSE, oldDefensivePoints, getDefensivePoints());
        propertySupport.firePropertyChange(ATTACK, oldAttackPoints, getAttackPoints());
        propertySupport.firePropertyChange(LIFE, oldLifePoints, getLifePoints());
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
        IRoom old = getRoom();
        IRoom newRoom = old.getRoomIn(direction);
        setOrientation(direction);
        EntityPositionKeeper.getInstance().move(this, newRoom);
        propertySupport.firePropertyChange(POSITION, old, newRoom);
    }

    @Override
    public void setLifePoints(int points) {
        if (points < 0) {
            throw new AssertionError();
        }
        this.lifePoints = points;
    }

    @Override
    public void setOrientation(Direction d) {
        if (d == null) {
            throw new NullPointerException();
        }
        orientation = d;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener l) {
        if (l == null) {
            throw new AssertionError("l'écouteur est null");
        }
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.addPropertyChangeListener(property, l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.removePropertyChangeListener(l);
    }
}
