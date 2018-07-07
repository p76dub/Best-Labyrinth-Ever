package model;

import model.interfaces.IRoom;
import model.interfaces.INetwork;
import util.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Pattern singleton. Le RoomNetwork est unique et partagé par toutes les pièces.
 * @inv <pre>
 *     RoomNetwork.getInstance() != null
 * </pre>
 */
public class RoomNetwork implements INetwork<IRoom, Direction> {
    // STATICS
    // Instance unique du réseau
    private static INetwork INSTANCE;

    /**
     * Obtenir l'instance unique RoomNetwork.
     * @return instance unique
     */
    public static INetwork getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoomNetwork();
        }
        return INSTANCE;
    }

    // ATTRIBUTES
    private final Map<IRoom, Map<Direction, IRoom>> network;

    private RoomNetwork() {
        network = new HashMap<>();
    }

    @Override
    public IRoom get(IRoom src, Direction d) {
        if (src == null || d == null) {
            throw new NullPointerException();
        }
        Map<Direction, IRoom> roomNeighbours = network.get(src);
        if (roomNeighbours == null) {
            return null;
        }
        return roomNeighbours.get(d);
    }

    @Override
    public void clear() {
        network.clear();
    }

    /**
     * @post <pre>
     *     Soit x:= old get(src, d)
     *          y:= old get(dst, d.opposite())
     *     x != null && x != dst ==> get(x, d.opposite()) == null
     *     y != null && y != src ==> get(y, d) == null
     *     get(src, d) == dst
     *     get(dst, d.opposite()) == srcd
     * </pre>
     */
    @Override
    public void create(IRoom src, Direction d, IRoom dst) {
        if (src == null || d == null || dst == null) {
            throw new NullPointerException();
        }
        IRoom neighbour = get(src, d);
        if (neighbour != null && !neighbour.equals(dst)) {
            network.get(neighbour).remove(d.opposite());
        }
        network.get(src).put(d, dst);
        network.get(dst).put(d.opposite(), src);
    }
}
