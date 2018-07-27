package model.generators;

import model.interfaces.IMazeGenerator;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

abstract public class AbstractGenerator implements IMazeGenerator {
    // ATTRIBUTES
    private final IRoom[][] rooms;

    // CONSTRUCTEUR
    /**
     * Créer un nouveau Generator, en lui donnant le tableau des pièces.
     * @param rooms l'ensemble des pièces du labyrinthe
     * @pre rooms != null && rooms.length > 0 && rooms[0].length > 0
     */
    public AbstractGenerator(IRoom[][] rooms) {
        if (rooms == null) {
            throw new NullPointerException();
        }
        if (rooms.length <= 0 || rooms[0].length <= 0) {
            throw new IllegalArgumentException();
        }
        this.rooms = rooms;
    }

    protected IRoom[][] getRooms() {
        return this.rooms;
    }

    // OUTILS
    /**
     * Sélectionne une pièce aléatoirement dans le labyrinthe et renvoie une nouvelle entrée à partir de celle-ci.
     * @return Une nouvelle entrée, contenant la pièce sélectionnée et ses coordonnées dans le labyrinthe
     */
    protected Entry pickRandomRoom() {
        Random generator = new Random();
        int x = generator.nextInt(this.rooms.length);
        int y = generator.nextInt(this.rooms[0].length);
        return new Entry(x, y, this.rooms[x][y]);
    }

    /**
     * Sélection un objet de type T dans une liste de candidats.
     * @param candidates la liste des candidats
     * @param <T> le type des objets
     * @return une candidat parmis ceux passés en paramètre
     * @pre candidates != null && candidates.size() > 0
     * @post candidates.contains(pickRandom(candidates))
     */
    protected  <T> T pickRandom(List<T> candidates) {
        assert candidates!= null && candidates.size() > 0;
        Random generator = new Random();
        return candidates.get(generator.nextInt(candidates.size()));
    }

    protected int getMazeWidth() {
        return this.rooms.length;
    }

    protected int getMazeHeight() {
        return this.rooms[0].length;
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
    protected Map<Direction, Entry> accessibleRoomsFrom(Entry src, List<Entry> opened, List<Entry> closed) {
        assert src != null && opened != null && closed != null;
        Map<Direction, Entry> result = new HashMap<>();
        final int x = src.getX();
        final int y = src.getY();

        if (isValidRoomCoordinates(x-1, y)) {
            Entry neighbour = new Entry(x-1, y, this.rooms[x-1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.WEST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x+1, y)) {
            Entry neighbour = new Entry(x+1, y, this.rooms[x+1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.EAST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y-1)) {
            Entry neighbour = new Entry(x, y-1, this.rooms[x][y-1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.NORTH, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y+1)) {
            Entry neighbour = new Entry(x, y+1, this.rooms[x][y+1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.SOUTH, neighbour);
            }
        }

        return result;
    }
}
