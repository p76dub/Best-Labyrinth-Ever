package model;

import model.interfaces.IMazeGenerator;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;

import java.util.*;

/**
 * Générateur de labyrinthe utilisant la méthode du GrowingTree :
 * <ol>
 *     <li>On sélectionne une cellule au hasard dans le labyrinthe : c'est le point de départ</li>
 *     <li>On ajoute cette cellule à la liste des cellules dites ouvertes, c'est à dire celles dont au moins une cellule
 *     voisine n'a pas encore été visitée</li>
 *     <li>Tant que la liste des cellules ouvertes n'est pas vide :
 *          <ol>
 *              <li>On sélectionne une cellule</li>
 *              <li>Si la cellule choisie n'a plus de cellules voisines disponibles, elle est supprimée de la liste des
 *              cellules ouvertes et placée dans celle des cellules fermées.</li>
 *              <li>Sinon, on choisit une cellule adjacente non visitée et on l'ajoute à la liste des cellules ouvertes
 *              </li>
 *          </ol>
 *     </li>
 * </ol>
 * La sélection de la prochaine cellule a explorer peut se faire de la manière suivante :
 * <ul>
 *     <li>En choisissant toujours la dernière cellule explorée</li>
 *     <li>En choisissant une cellule au hasard</li>
 *     <li>En choisissant la dernière cellule explorée et, de temps en temps, une cellule au hasard</li>
 *     <li>En choisissant une cellule parmis les N dernières explorées</li>
 * </ul>
 */
public class GrowingTreeGenerator implements IMazeGenerator {
    // INTERNAL CLASS
    /**
     * Représente une entrée utilisée pour maintenir la liste des pièces visitées / fermées. Les objets de type Entry
     * sont non mutables.
     * @inv getRoom() != null
     */
    private class Entry {
        private final int x;
        private final int y;
        private final IRoom room;

        /**
         * Créer une nouvelle entrée avec la pièce fournie, située dans la labyrinthe à la position (x,y)
         * @param x position (colonne) de la pièce
         * @param y position (ligne) de la pièce
         * @param room la pièce stockée dans l'entrée
         * @pre room != null
         * @post <pre>
         *     getRoom().equals(room)
         *     getX() == x
         *     getY() == y
         * </pre>
         */
        public Entry(int x, int y, IRoom room) {
            if (room == null) {
                throw new NullPointerException();
            }
            this.x = x;
            this.y = y;
            this.room = room;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public IRoom getRoom() {
            return room;
        }

        /**
         * Comparaison entre deux objets, la cible et un autre. Indique si les deux objets sont identiques, c'est à dire
         * que la pièce est les coordonnées sont identiques.
         * @param obj l'objet avec lequels la comparaison est faite
         * @return true si les deux objets sont identiques, false sinon
         */
        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj.getClass().equals(this.getClass())) {
                Entry e = (Entry) obj;
                return e.getRoom().equals(getRoom()) && e.getX() == getX() && e.getY() == getY();
            }
            return false;
        }
    }

    // ATTRIBUTS
    private final IRoom[][] rooms;
    private IRoom entry;
    private IRoom exit;
    private IRoom princess;

    // CONSTRUCTEUR
    public GrowingTreeGenerator(IRoom[][] rooms) {
        if (rooms == null) {
            throw new NullPointerException();
        }
        this.rooms = rooms;
    }
    @Override
    public IRoom getEntry() {
        return entry;
    }

    @Override
    public IRoom getExit() {
        return exit;
    }

    @Override
    public IRoom getPrincessRoom() {
        return princess;
    }

    @Override
    public void generate() {
        // Garder une trace des pièces visitées
        List<Entry> opened = new LinkedList<>();
        List<Entry> closed = new ArrayList<>();
        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        opened.add(pickRandomRoom());

        while (opened.size() != 0) {
            final Entry src = pickRandom(opened);

            Map<Direction, Entry> candidates = accessibleRoomsFrom(src, opened, closed);
            if (candidates.size() > 0) {
                // Choisir une direction
                Direction d = pickRandom(new ArrayList<>(candidates.keySet()));

                // Ajout de la pièce dans les pièces visitées
                Entry dst = candidates.get(d);
                opened.add(dst);

                // Break the wall
                network.create(src.getRoom(), d, dst.getRoom());
            } else {
                // Ajout de la pièce dans les pièces complètement explorées
                opened.remove(src);
                closed.add(src);
            }
        }
    }

    // OUTILS

    /**
     * Sélectionne une pièce aléatoirement dans le labyrinthe et renvoie une nouvelle entrée à partir de celle-ci.
     * @return Une nouvelle entrée, contenant la pièce sélectionnée et ses coordonnées dans le labyrinthe
     */
    private Entry pickRandomRoom() {
        Random generator = new Random();
        int x = generator.nextInt(rooms.length);
        int y = generator.nextInt(rooms[0].length);
        return new Entry(x, y, rooms[x][y]);
    }

    /**
     * Sélection un objet de type T dans une liste de candidats.
     * @param candidates la liste des candidats
     * @param <T> le type des objets
     * @return une candidat parmis ceux passés en paramètre
     * @pre candidates != null && candidates.size() > 0
     * @post candidates.contains(pickRandom(candidates))
     */
    private <T> T pickRandom(List<T> candidates) {
        assert candidates!= null && candidates.size() > 0;
        Random generator = new Random();
        return candidates.get(generator.nextInt(candidates.size()));
    }

    private int getMazeWidth() {
        return this.rooms.length;
    }

    private int getMazeHeight() {
        return this.rooms[0].length;
    }

    /**
     * Indique si les coordonnées fournies sont bien des coordonnées valides pour le labyrinthe courant.
     * @param x abscisse
     * @param y ordonnée
     * @return true si les coordonnées sont valides, false sinon
     * @post isValidRoomCoordinates(x, y) <==> 0 <= x < getMazeWidth() && 0 <= y < getMazeHeight()
     */
    private boolean isValidRoomCoordinates(int x, int y) {
        return 0 <= x && x < getMazeWidth() && 0 <= y && y < getMazeHeight();
    }

    private Map<Direction, Entry> accessibleRoomsFrom(Entry src, List<Entry> opened, List<Entry> closed) {
        Map<Direction, Entry> result = new HashMap<>();
        final int x = src.getX();
        final int y = src.getY();

        if (isValidRoomCoordinates(x-1, y)) {
            Entry neighbour = new Entry(x-1, y, rooms[x-1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.WEST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x+1, y)) {
            Entry neighbour = new Entry(x+1, y, rooms[x+1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.EAST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y-1)) {
            Entry neighbour = new Entry(x, y-1, rooms[x][y-1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.NORTH, neighbour);
            }
        }

        if (isValidRoomCoordinates(x, y+1)) {
            Entry neighbour = new Entry(x, y+1, rooms[x][y+1]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.SOUTH, neighbour);
            }
        }

        return result;
    }
}
