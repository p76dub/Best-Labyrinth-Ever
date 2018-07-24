package model;

import model.interfaces.IItem;
import model.interfaces.IMaze;
import model.interfaces.IRoom;
import util.Direction;

public class Room implements IRoom {
    // ATTRIBUTES
    private final IMaze maze;
    private IItem item;

    // CONSTRUCTOR
    /**
     * Construire une nouvelle pièce. Pour ce faire, il suffit de fournir une référence vers le labyrinthe dans lequel
     * la pièce sera déployée.
     * @param parent le labyrinthe
     * @pre <pre>
     *     parent != null
     * </pre>
     */
    Room(IMaze parent) {
        if (parent == null) {
            throw new AssertionError();
        }
        this.maze = parent;
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
        return null;
    }
}
