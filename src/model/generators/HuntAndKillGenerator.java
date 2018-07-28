package model.generators;

import model.RoomNetwork;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

import java.util.*;

class HuntAndKillGenerator extends AbstractGenerator {
    // ATTRIBUTS
    private final float huntProbability;

    // CONSTRUCTEUR
    public HuntAndKillGenerator(IRoom[][] rooms, float huntProbability) {
        super(rooms);
        if (0 > huntProbability || huntProbability > 1) {
            throw new IllegalArgumentException();
        }
        this.huntProbability = huntProbability;
    }

    @Override
    public IRoom getEntry() {
        return null;
    }

    @Override
    public IRoom getExit() {
        return null;
    }

    @Override
    public IRoom getPrincessRoom() {
        return null;
    }

    @Override
    public void generate() {
        // On stocke les pièces visitées et les candidates pour le hunt & kill
        Set<Entry> huntCandidates = new HashSet<>();
        List<Entry> closed = new ArrayList<>();

        // Raccourci vers le network
        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        // Selection du point d'entrée
        Entry src = pickRandomRoom();

        // On stocke les voisins
        Map<Direction, Entry> neighbours = accessibleRoomsFrom(src, closed, new ArrayList<>());

        // On ajoute les pièces découvertes (sauf la pièce sélectionnée)
        huntCandidates.addAll(neighbours.values());

        // Random Generator
        Random generator = new Random();

        while(neighbours.size() != 0 || huntCandidates.size() != 0) {
            // La cellule a été explorée, on la change donc de liste
            closed.add(src);

            // Generate a new Random number
            float num = generator.nextFloat();

            if (neighbours.size() == 0 || num <= this.huntProbability) {
                // Hunt & Kill mode
                src = pickRandom(new ArrayList<>(huntCandidates));
                connectToClosestClosedRoom(src, closed);
            } else {
                // On sélectionne une pièce disponible et on la connecte à la pièce courante
                Direction d = pickRandom(new ArrayList<>(neighbours.keySet()));
                network.create(src.getRoom(), d, neighbours.get(d).getRoom());

                // On change la cellule courante
                src = neighbours.get(d);
            }

            // On supprime src de la liste des candidates pour le hunt & kill
            huntCandidates.remove(src);

            // On génère les pièces voisines non visitées et on les ajoute comme candidates pour le hunt & kill
            neighbours = accessibleRoomsFrom(src, closed, new ArrayList<>());
            huntCandidates.addAll(neighbours.values());
        }
    }

    // OUTILS
    private void connectToClosestClosedRoom(Entry src, List<Entry> closed) {
        int x = src.getX();
        int y = src.getY();

        // Raccourcis
        IRoom[][] rooms = getRooms();
        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        // La pièce à laquelle se connecter, avec la direction
        IRoom dst = null;
        Direction d = null;

        if (isValidRoomCoordinates(x, y + 1)) {
            Entry room = new Entry(x, y+1, rooms[x][y+1]);
            if (closed.contains(room)) {
                d = Direction.SOUTH;
                dst = room.getRoom();
            }
        }
        if (isValidRoomCoordinates(x, y - 1)) {
            Entry room = new Entry(x, y-1, rooms[x][y-1]);
            if (closed.contains(room)) {
                d = Direction.NORTH;
                dst = room.getRoom();
            }
        }
        if (isValidRoomCoordinates(x + 1, y)) {
            Entry room = new Entry(x+1, y, rooms[x+1][y]);
            if (closed.contains(room)) {
                d = Direction.EAST;
                dst = room.getRoom();
            }
        }
        if (isValidRoomCoordinates(x - 1, y)) {
            Entry room = new Entry(x-1, y, rooms[x-1][y]);
            if (closed.contains(room)) {
                d = Direction.WEST;
                dst = room.getRoom();
            }
        }

        network.create(src.getRoom(), d, dst);
    }
}
