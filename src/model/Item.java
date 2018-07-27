package model;

import model.interfaces.IItem;
import model.interfaces.IRoom;

import java.nio.file.Path;

public class Item implements IItem {
    // ATTRIBUTS
    private final Path image;
    private final int attack;
    private final int defense;
    private final int lifePoints;
    private boolean taken;
    private final String message;
    private final IRoom location;

    // CONSTRUCTEUR
    /**
     * Créer un nouvel item avec un message affiché, le chemin vers une image, un bonus/malus d'attaque, de défense et
     * de vie. Enfin, il faut fournir la pièce dans laquelle il sera déployé.
     * @param message message affiché
     * @param image chemin vers l'image
     * @param attack points d'attaque
     * @param defense points de défense
     * @param life points de ve
     * @param room pièce dans laquelle est déployé l'item
     * @pre <pre>
     *     message != null
     *     image != null
     *     room != null
     * </pre>
     * @post <pre>
     *     getMessage().equals(message)
     *     getImagePath().equals(image)
     *     getAttackPoints() == attack
     *     getDefensivePoints() == defense
     *     getLifePoints() == life
     *     getRoom().equals(room)
     * </pre>
     */
    public Item(String message, Path image, int attack, int defense, int life, IRoom room) {
        if (message == null || image == null || room == null) {
            throw new AssertionError();
        }
        this.message = message;
        this.image = image;
        this.attack = attack;
        this.defense = defense;
        this.lifePoints = life;
        this.location = room;
    }

    @Override
    public Path getImagePath() {
        return image;
    }

    @Override
    public int getAttackPoints() {
        return attack;
    }

    @Override
    public int getDefensivePoints() {
        return defense;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public boolean isTaken() {
        return taken;
    }

    @Override
    public IRoom getRoom() {
        return location;
    }

    @Override
    public String getMessage() {
        return message;
    }

    // COMMANDE
    @Override
    public void take() {
        this.taken = true;
        //TODO changement
        getRoom().setItem(null);
    }
}
