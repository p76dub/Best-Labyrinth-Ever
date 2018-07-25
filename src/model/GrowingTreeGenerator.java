package model;

import model.interfaces.IMazeGenerator;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;

import java.util.*;

public class GrowingTreeGenerator implements IMazeGenerator {
    // INTERNAL CLASS
    private class Entry {
        private final int x;
        private final int y;
        private final IRoom room;

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
        // Keep track of visited rooms
        Set<Entry> opened = new HashSet<>();
        List<Entry> closed = new ArrayList<>();
        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        opened.add(pickRandomRoom());

        while (opened.size() != 0) {
            final Entry src = opened.iterator().next();

            Map<Direction, Entry> candidates = accessibleRoomsFrom(src, new ArrayList<>(opened), closed);
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
    private Entry pickRandomRoom() {
        Random generator = new Random();
        int x = generator.nextInt(rooms.length);
        int y = generator.nextInt(rooms[0].length);
        return new Entry(x, y, rooms[x][y]);
    }

    private <T> T pickRandom(List<T> candidates) {
        Random generator = new Random();
        return candidates.get(generator.nextInt(candidates.size()));
    }

    private int getMazeWidth() {
        return this.rooms.length;
    }

    private int getMazeHeight() {
        return this.rooms[0].length;
    }

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
                result.put(Direction.EAST, neighbour);
            }
        }

        if (isValidRoomCoordinates(x+1, y)) {
            Entry neighbour = new Entry(x+1, y, rooms[x+1][y]);
            if (!opened.contains(neighbour) && !closed.contains(neighbour)) {
                result.put(Direction.WEST, neighbour);
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
