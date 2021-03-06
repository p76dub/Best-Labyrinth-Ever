package model.maze;

import model.RoomNetwork;
import model.interfaces.IMaze;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

import java.net.URISyntaxException;
import java.util.*;

class BackTrackingGenerator extends AbstractGenerator {
    public BackTrackingGenerator(int width, int height) throws URISyntaxException {
        super(width, height);
    }

    @Override
    public void generate() {
        Deque<Entry> stack = new LinkedList<>();
        Set<Entry> closed = new HashSet<>();
        INetwork<IRoom, Direction> network = getMaze().getNetwork();

        stack.push(pickRandomRoom());
        while (!stack.isEmpty()) {
            // On récupère la dernière pièce visité
            Entry src = stack.pop();

            // On construit la liste des voisines non visitées
            Map<Direction, Entry> neighbours = accessibleRoomsFrom(src, stack, closed);

            if (neighbours.size() != 0) {
                // On choisit une voisine au hasard, on la connecte et on l'ajoute à la pile
                Direction d = pickRandom(new ArrayList<>(neighbours.keySet()));
                network.create(src.getRoom(), d, neighbours.get(d).getRoom());

                stack.push(src);
                stack.push(neighbours.get(d));
            } else {
                closed.add(src);
            }
        }
        placeSpecialRooms();
    }
}
