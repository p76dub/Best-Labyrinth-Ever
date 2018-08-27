package model.maze;

import model.interfaces.IMaze;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

import java.net.URISyntaxException;
import java.util.*;

abstract class AbstractGenerator implements IMazeGenerator {
    // ATTRIBUTES
    private Maze maze;

    // CONSTRUCTOR
    public AbstractGenerator(int width, int height) throws URISyntaxException {
        if (width < 2 || height < 2) {
            throw new IllegalArgumentException();
        }
        maze = new Maze(width, height);
    }

    // REQUETE
    public final IMaze getMaze() {
        return maze;
    }

    @Override
    public IRoom getEntry() throws MazeNotYetGeneratedException {
        if (maze == null) {
            throw new MazeNotYetGeneratedException();
        }
        return getMaze().entry();
    }

    @Override
    public IRoom getExit() throws MazeNotYetGeneratedException {
        if (maze == null) {
            throw new MazeNotYetGeneratedException();
        }
        return getMaze().exit();
    }

    @Override
    public IRoom getPrincessRoom() throws MazeNotYetGeneratedException {
        if (maze == null) {
            throw new MazeNotYetGeneratedException();
        }
        return getMaze().getPrincess().getRoom();
    }

    // OUTILS
    /**
     * Sélectionne une pièce aléatoirement dans le labyrinthe et renvoie une nouvelle entrée à partir de celle-ci.
     * @return Une nouvelle entrée, contenant la pièce sélectionnée et ses coordonnées dans le labyrinthe
     */
    protected Entry pickRandomRoom() {
        Random generator = new Random();
        int x = generator.nextInt(getMazeWidth());
        int y = generator.nextInt(getMazeHeight());
        return new Entry(x, y, this.getMaze().getRooms()[x][y]);
    }

    /**
     * Sélection un objet de type T dans une liste de candidats.
     * @param candidates la liste des candidats
     * @param <T> le type des objets
     * @return une candidat parmis ceux passés en paramètre
     * @pre candidates != null && candidates.size() > 0
     * @post candidates.contains(pickRandom(candidates))
     */
    protected <T> T pickRandom(List<T> candidates) {
        assert candidates!= null && candidates.size() > 0;
        Random generator = new Random();
        return candidates.get(generator.nextInt(candidates.size()));
    }

    protected int getMazeWidth() {
        return this.maze.colsNb();
    }

    protected int getMazeHeight() {
        return this.maze.rowsNb();
    }

    /**
     * Indique si les coordonnées fournies sont bien des coordonnées valides pour le labyrinthe courant.
     * @param x abscisse
     * @param y ordonnée
     * @return true si les coordonnées sont valides, false sinon
     * @post isValidRoomCoordinates(x, y) <==> 0 <= x < getMazeWidth() && 0 <= y < getMazeHeight()
     */
    protected boolean isValidRoomCoordinates(int x, int y) {
        return 0 <= x && x < getMazeWidth() && 0 <= y && y < getMazeHeight();
    }

    /**
     * Obtenir l'ensemble des pièces à côté de la pièce source. Les pièces récupérées sont toutes des pièces non
     * visitées.
     * @param src La pièce source
     * @param opened la liste des pièces dites "ouvertes"
     * @param closed la liste des pièces dites "fermées"
     * @return la liste, éventuellement vide, des pièces correspondantes aux critères
     * @pre <pre>
     *      src != null
     *      opened != null
     *      closed != null
     * </pre>
     * @post accessibleRoomsFrom(src, opened, closed) != null
     */
    protected Map<Direction, Entry> accessibleRoomsFrom(Entry src, Collection<Entry> opened, Collection<Entry> closed) {
        assert src != null && opened != null && closed != null;
        Map<Direction, Entry> result = new HashMap<>();
        final int x = src.getX();
        final int y = src.getY();

        if (isValidRoomCoordinates(x-1, y)) {
            Entry neighbour = new Entry(x-1, y, this.maze.getRooms()[x-1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.WEST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x+1, y)) {
            Entry neighbour = new Entry(x+1, y, this.maze.getRooms()[x+1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.EAST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y-1)) {
            Entry neighbour = new Entry(x, y-1, this.maze.getRooms()[x][y-1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.NORTH, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y+1)) {
            Entry neighbour = new Entry(x, y+1, this.maze.getRooms()[x][y+1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.SOUTH, neighbour);
            }
        }

        return result;
    }

    protected void placeSpecialRooms() {
        IRoom entry = pickDeadEndRoom();
        maze.setEntry(entry);

        IRoom exit = pickDeadEndRoom();
        while (exit.equals(entry)) {
            exit = pickDeadEndRoom();
        }
        maze.setExit(exit);

        IRoom princessRoom = pickRandomRoom().getRoom();
        while (princessRoom.equals(entry) || princessRoom.equals(exit)) {
            princessRoom = pickRandomRoom().getRoom();
        }
        getMaze().getPrincess().setRoom(princessRoom);
    }

    private IRoom pickDeadEndRoom() {
        IRoom deadEnd = pickRandomRoom().getRoom();
        while (countNeighbours(deadEnd) > 1) {
            deadEnd = pickRandomRoom().getRoom();
        }
        return deadEnd;
    }

    private int countNeighbours(IRoom room) {
        int count = 0;
        for (Direction d : Direction.allDirections()) {
            if (room.canExitIn(d)) {
                count += 1;
            }
        }
        return count;
    }
}
