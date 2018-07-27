package model.generators;

import model.RoomNetwork;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;
import util.Entry;

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
class GrowingTreeGenerator extends AbstractGenerator {
    // ATTRIBUTS
    private IRoom entry;
    private IRoom exit;
    private IRoom princess;

    // CONSTRUCTEUR
    public GrowingTreeGenerator(IRoom[][] rooms) {
        super(rooms);
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
}
