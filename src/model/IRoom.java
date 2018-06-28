package model;

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

    // Commandes
    /**
     * Connecte cette salle à la salle r dans la direction d.
     * @param d, r
     * @pre <pre>
     *     d != null
     *     r != null
     *     !canExitIn(d)
     *     !r.canExitIn(d.opposite()) </pre>
     * @post <pre>
     *     getRoomIn(d) == r
     *     getDoorIn(d) == null </pre>
     */
    void breakWall(Direction d, IRoom r);
}