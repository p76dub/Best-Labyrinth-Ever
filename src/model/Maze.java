package model;

import model.interfaces.IMaze;
import model.interfaces.IMazeGenerator;
import model.interfaces.IRoom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Maze implements IMaze {
    // STATICS
    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 15;

    // ATTRIBUTS
    private final IRoom[][] rooms;

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
     */
    public Maze(int width, int height) {
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
    }

    /**
     * Créer un labyrinthe de largeur et de hauteur identique, valant size.
     * @param size taille du labyrinthe
     * @pre size > 0
     * @post colsNb() == rowsNb() == size
     */
    public Maze(int size) {
        this(size, size);
    }

    /**
     * Créer un labyrinthe avec une hauteur et une largeur par défaut.
     */
    public Maze() {
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
        return null;
    }

    @Override
    public IRoom exit() {
        return null;
    }

    @Override
    public IRoom[][] getRooms() {
        return rooms;
    }

    @Override
    public void mark(IRoom r) {

    }
}
