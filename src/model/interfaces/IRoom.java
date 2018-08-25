package model.interfaces;

import util.Direction;

import java.beans.PropertyChangeListener;
import java.util.Collection;

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
     * Modifie l'item de la pièce ou null s'il n'y en a pas.
     * @param it
     */
    void setItem(IItem it);

    /**
     * Retourne l'item de la pièce ou null s'il n'y en a pas.
     * @return item
     */
    IItem getItem();

    /**
     * Retourne le labyrinthe associé de la pièce.
     * @return maze
     */
    IMaze getMaze();

    Collection<IEntity> getEntities();

    void addPropertyChangeListener(String property, PropertyChangeListener l);

    void removePropertyChangeListener(PropertyChangeListener l);
}