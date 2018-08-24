package model;

import model.interfaces.IPrincess;
import model.interfaces.IRoom;

import java.net.URI;

public class Princess implements IPrincess {

    // ATTRIBUTS
    private final URI imagePath;
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
     *     getMazeImagePath().equals(image)
     *     getRoom().equals(room)
     *     !safe
     * </pre>
     */
    public Princess(String message, URI image, IRoom room) {
        //TODO image != null
        if (message == null || room == null) {
            throw new AssertionError();
        }
        this.message = message;
        this.imagePath = image;
        this.location = room;
        this.safe = false;
    }

    // REQUETES
    @Override
    public URI getImagePath() {
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
