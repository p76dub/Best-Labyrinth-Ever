package model.maze;

import model.Princess;
import model.Room;
import model.RoomNetwork;
import model.interfaces.IMaze;
import model.interfaces.INetwork;
import model.interfaces.IPrincess;
import model.interfaces.IRoom;
import util.Direction;

import java.net.URISyntaxException;

class Maze implements IMaze {
    // STATICS
    private static final String PRINCESS_MESSAGE = "Merci de m'avoir sauvé merciiire";

    // ATTRIBUTS
    private final IRoom[][] rooms;
    private IRoom entry;
    private IRoom exit;
    private final IPrincess princess;
    private final INetwork<IRoom, Direction> network;

    // CONSTRUCTOR
    /**
     * Créer un nouveau labyrinthe, de largeur width et de hauteur height.
     * @param width largeur du labyrinthe
     * @param height hauteur d labyrinthe
     * @pre <pre>
     *     width > 0
     *     height > 0
     * </pre>
     * @post <pre>
     *     rowsNb() == height
     *     colsNb() == width
     * </pre>
     * @throws URISyntaxException if the princess picture uri is malformed
     */
    Maze(int width, int height) throws URISyntaxException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        rooms = new IRoom[width][];
        for (int i = 0; i < width; ++i) {
            rooms[i] = new IRoom[height];
            for (int j = 0; j < height; ++j) {
                rooms[i][j] = new Room(this);
            }
        }
        network = new RoomNetwork();

        princess = new Princess(
            PRINCESS_MESSAGE,
            getClass().getResource("../../coeur.png").toURI(),
            null
        );
    }

    /**
     * Créer un labyrinthe de largeur et de hauteur identique, valant size.
     * @param size taille du labyrinthe
     * @pre size > 0
     * @post colsNb() == rowsNb() == size
     */
    Maze(int size) throws URISyntaxException {
        this(size, size);
    }

    /**
     * Créer un labyrinthe avec une hauteur et une largeur par défaut.
     */
    Maze() throws URISyntaxException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public int rowsNb() {
        return rooms[0].length;
    }

    @Override
    public int colsNb() {
        return rooms.length;
    }

    @Override
    public IRoom entry() {
        return entry;
    }

    @Override
    public IRoom exit() {
        return exit;
    }

    @Override
    public IRoom[][] getRooms() {
        return rooms;
    }

    @Override
    public IPrincess getPrincess() {
        return princess;
    }

    @Override
    public INetwork<IRoom, Direction> getNetwork() {
        return network;
    }

    @Override
    public void mark(IRoom r) {

    }

    void setEntry(IRoom room) {
        if (room == null) {
            throw new NullPointerException();
        }
        this.entry = room;
    }

    void setExit(IRoom room) {
        if (room == null) {
            throw new NullPointerException();
        }
        this.exit = room;
    }
}
