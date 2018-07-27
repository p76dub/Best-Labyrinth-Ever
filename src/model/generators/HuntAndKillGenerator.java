package model.generators;

import model.RoomNetwork;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

import java.util.*;

public class HuntAndKillGenerator extends AbstractGenerator {
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
        // Store all candidates and visited rooms
        Set<Entry> huntCandidates = new HashSet<>();
        List<Entry> closed = new ArrayList<>();

        // Keep a reference to the RoomNetwork
        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        // Select the entry point
        Entry src = pickRandomRoom();
        huntCandidates.add(src);

        // Random Generator
        Random generator = new Random();

        while(huntCandidates.size() != 0) {
            Map<Direction, Entry> neighbours = accessibleRoomsFrom(src, closed, new ArrayList<>());

            // La cellule a été explorée, on la change donc de liste
            closed.add(src);
            huntCandidates.remove(src);

            // Generate a new Random number
            float num = generator.nextFloat();

            if (neighbours.size() == 0 || num <= this.huntProbability) {
                // Hunt & Kill mode
                src = pickRandom(new ArrayList<>(huntCandidates));
            } else {
                // Create the link between the selected Room and src
                Direction d = pickRandom(new ArrayList<Direction>(neighbours.keySet()));
                network.create(src.getRoom(), d, neighbours.get(d).getRoom());

                // On change la cellule courante
                src = neighbours.get(d);

                // On ajoute les pièces découvertes
                huntCandidates.addAll(neighbours.values());
            }
        }
    }
}
