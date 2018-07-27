package model;

import model.interfaces.IItem;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import model.interfaces.IRoom;
import util.Direction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Room implements IRoom {
    // ATTRIBUTES
    private final IMaze maze;
    private IItem item;
    private IPlayer player;
    private Direction direction;
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
        this.player = null;
        propertySupport = new PropertyChangeSupport(this);
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
    public IPlayer getPlayer() { return player;}

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setPlayer(IPlayer player, Direction d) {
        IPlayer oldPlayer = getPlayer();
        this.player = player;
        this.direction = d;
        propertySupport.firePropertyChange("PLAYER", oldPlayer, player);
        //TODO a changer !
        if (player != null && getItem() != null) {
            player.take(getItem());
        }
    }

    public void addPropertyChangeListener(String property,
                                          PropertyChangeListener l) {
        if (l != null) {
            new AssertionError("l'écouteur est null");
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
