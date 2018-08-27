package model;

import model.interfaces.IPrincess;
import model.interfaces.IRoom;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;

public class Princess implements IPrincess {

    // ATTRIBUTS
    private final URI imagePath;
    private final String message;
    private IRoom location;
    private PropertyChangeSupport propertySupport;
    private boolean safe;

    // CONSTRUCTEUR
    /**
     * Instancie une princesse avec les paramètres fournis.
     * @param message le message affiché lorsqu'un joueur rencontre la princesse
     * @param image le chemin vers l'image de la princesse
     * @param room la pièce où se situe la princesse
     * @pre <pre>
     *     message != null && image != null
     * </pre>
     * @post <pre>
     *     getMessage().equals(message)
     *     getMazeImagePath().equals(image)
     *     room != null => getRoom().equals(room)
     *     !safe
     * </pre>
     */
    public Princess(String message, URI image, IRoom room) {
        if (message == null || image == null) {
            throw new AssertionError();
        }
        this.message = message;
        this.imagePath = image;
        this.location = room;
        this.safe = false;
        propertySupport = new PropertyChangeSupport(this);
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
        propertySupport.firePropertyChange(SAFE_PROPERTY, false, true);
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

    @Override
    public void setRoom(IRoom room) {
        if (room == null) {
            throw new NullPointerException();
        }
        this.location = room;
    }
}
