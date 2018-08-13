package model;

import model.interfaces.*;
import util.Direction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

public class Room implements IRoom {
    //STATICS
    public static final String ENTITIES_PROPERTY = "entities";

    // ATTRIBUTES
    private final IMaze maze;
    private IItem item;
    private PropertyChangeSupport propertySupport;

    // CONSTRUCTOR
    /**
     * Construire une nouvelle pièce. Pour ce faire, il suffit de fournir une référence vers le labyrinthe dans lequel
     * la pièce sera déployée.
     * @param parent le labyrinthe
     * @pre <pre>
     *     parent != null
     * </pre>
     */
    public Room(IMaze parent) {
        if (parent == null) {
            throw new AssertionError();
        }
        this.maze = parent;
        propertySupport = new PropertyChangeSupport(this);

        EntityPositionKeeper.getInstance().addPropertyChangeListener(
            EntityPositionKeeper.ROOM_PROPERTY,
            evt -> {
                IRoom updatedRoom = (IRoom) evt.getNewValue();
                if (updatedRoom.equals(Room.this)) {
                    Room.this.propertySupport.firePropertyChange(ENTITIES_PROPERTY, null, getEntities());
                }
            }
        );
    }

    @Override
    public boolean canExitIn(Direction d) {
        return RoomNetwork.getInstance().get(this, d) != null;
    }

    @Override
    public IRoom getRoomIn(Direction d) {
        return RoomNetwork.getInstance().get(this, d);
    }

    @Override
    public IItem getItem() {
        return item;
    }

    //TODO changement
    @Override
    public void setItem(IItem it) {
        IItem oldItem = getItem();
        item = it;
        if (it == null) {
            propertySupport.firePropertyChange("TAKE", oldItem, it);
        }
    }

    @Override
    public Collection<IEntity> getEntities() {
        return EntityPositionKeeper.getInstance().getEntities(this);
    }

    public void addPropertyChangeListener(String property,
                                          PropertyChangeListener l) {
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

    // OUTILS
}
