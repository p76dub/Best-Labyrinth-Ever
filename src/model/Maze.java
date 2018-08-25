package model;

import model.interfaces.IMaze;
import model.interfaces.IPrincess;
import model.interfaces.IRoom;

import java.net.URISyntaxException;
import java.util.Random;

public class Maze implements IMaze {
    // STATICS
    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 15;
    private static final String PRINCESS_MESSAGE = "Merci messiiiirre";

    // ATTRIBUTS
    private final IRoom[][] rooms;
    private final IRoom entry;
    private final IRoom exit;
    private final IPrincess princess;

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
    public Maze(int width, int height) throws URISyntaxException {
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

        entry = pickRandomRoom();

        IRoom exit = pickRandomRoom();
        while (exit.equals(entry)) {
            exit = pickRandomRoom();
        }
        this.exit = exit;

        IRoom princessRoom = pickRandomRoom();
        while (princessRoom.equals(exit) || princessRoom.equals(entry)) {
            princessRoom = pickRandomRoom();
        }
        //TODO IMAGE
        princess = new Princess(
            PRINCESS_MESSAGE,
                getClass().getResource("../coeur.png").toURI(),
                princessRoom
        );
    }

    /**
     * Créer un labyrinthe de largeur et de hauteur identique, valant size.
     * @param size taille du labyrinthe
     * @pre size > 0
     * @post colsNb() == rowsNb() == size
     */
    public Maze(int size) throws URISyntaxException {
        this(size, size);
    }

    /**
     * Créer un labyrinthe avec une hauteur et une largeur par défaut.
     */
    public Maze() throws URISyntaxException {
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
    public void mark(IRoom r) {

    }

    // OUTILS
    private IRoom pickRandomRoom() {
        Random generator = new Random();
        int x = generator.nextInt(rowsNb());
        int y = generator.nextInt(colsNb());
        return getRooms()[y][x];
    }
}
