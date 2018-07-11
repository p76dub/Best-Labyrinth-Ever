package model;

import model.interfaces.IPrincess;
import model.interfaces.IRoom;

import java.nio.file.Path;

public class Princess implements IPrincess {

    // ATTRIBUTS
    private final Path imagePath;
    private final String message;
    private IRoom location;
    private boolean safe;

    // CONSTRUCTEUR
    /**
     * Instancie une princesse avec les paramètres fournis.
     * @param message le message affiché lorsqu'un joueur rencontre la princesse
     * @param image le chemin vers l'image de la princesse
     * @param room la pièce où se situe la princesse
     * @pre <pre>
     *     message != null && image != null && room != null
     * </pre>
     * @post <pre>
     *     getMessage().equals(message)
     *     getImagePath().equals(image)
     *     getRoom().equals(room)
     *     !safe
     * </pre>
     */
    public Princess(String message, Path image, IRoom room) {
        if (message == null || image == null || room == null) {
            throw new AssertionError();
        }
        this.message = message;
        this.imagePath = image;
        this.location = room;
        this.safe = false;
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
    public boolean isSafe() { return safe; }

    @Override
    public IRoom getRoom() { return location; }

    //METHODES
    @Override
    public void save() {
        safe = true;
    }
}
