package model;

import model.interfaces.IEnemy;
import model.interfaces.IEntity;
import model.interfaces.IRoom;
import util.Direction;

import java.nio.file.Path;

/**
 * Les objets de type Enemy représentent les ennemis des joueurs dans le labyrinthe. En plus des méthodes standards de
 * toutes les entités, les ennemis possèdent une image et un message affiché lorsque le joueur les rencontre.
 */
class Enemy implements IEnemy {
    // ATTRIBUTS
    private final Path imagePath;
    private final String name;
    private final String message;
    private final int attackPoints;
    private final int defensivePoints;
    private int lifePoints;
    private IRoom location;

    // CONSTRUCTEUR
    /**
     * Instancie un nouvel ennemis avec les paramètres fournis.
     * @param name le nom de l'ennemi
     * @param message le message affiché lorsqu'un joueur rencontre cet ennemi
     * @param image le chemin vers l'image de l'enemi
     * @param attack les points d'attaque
     * @param defense les points de défense
     * @param initialLife le nombre de points de vie initialiement
     * @pre <pre>
     *     name != null && message != null && image != null && attack > 0 && defense >= 0 && initialLife > 0
     * </pre>
     * @post <pre>
     *     getName().equals(name)
     *     getMessage().equals(message)
     *     getImagePath().equals(image)
     *     getAttackPoints() == attack
     *     getDefensePoints() == defense
     *     getLifePoints() == initialLife
     *     getRoom() == null
     * </pre>
     */
    public Enemy(String name, String message, Path image, int attack, int defense, int initialLife) {
        if (name == null || message == null || attack <= 0 || defense > 100 || defense < 0 || initialLife <= 0) {
            throw new AssertionError();
        }
        this.name = name;
        this.message = message;
        this.imagePath = image;
        this.attackPoints = attack;
        this.defensivePoints = defense;
        this.lifePoints = initialLife;
    }

    // REQUETES
    @Override
    public Path getImagePath() {
        return imagePath;
    }

    @Override
    public String getMessage() {
        return message;
    }

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
        return defensivePoints;
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
        this.location = getRoom().getRoomIn(direction);
    }

    @Override
    public void setLifePoints(int points) {
        if (points < 0) {
            throw new AssertionError();
        }
        this.lifePoints = points;
    }
}
