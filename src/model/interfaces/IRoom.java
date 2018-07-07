package model.interfaces;

import util.Direction;

/**
 * Modélise les salles du labyrinthe.
 * Les salles sont reliées entre elles et le joueur les traverse.
 * @inv <pre>
 *     getRoomIn(d) == r <==> r.getRoomIn(d.opposite()) == this
 *     getRoomIn(d) != null
 *         ==> getRoomIn(d).getDoorIn(d.opposite()) == getDoorIn(d)
 *     getRoomIn(d) == null ==> getDoorIn(d) == null
 *     canExitIn(d) <==> getRoomIn(d) != null && (getDoorIn(d) == null)
 *     </pre>
 */
public interface IRoom {

    // Requêtes
    /**
     * Indique s'il y a un passage dans la direction d.
     * @param d
     * @pre d != null
     * @return
     */
    boolean canExitIn(Direction d);

    /**
     * La salle accessible dans la direction d à partir de cette salle,
     * ou null sinon.
     * @param d
     * @pre d != null
     */
    IRoom getRoomIn(Direction d);

    /**
     * Retourne l'item de la pièce ou null s'il n'y en a pas.
     * @return item
     */
    IItem getItem();
}