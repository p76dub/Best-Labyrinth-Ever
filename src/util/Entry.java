package util;

import model.interfaces.IRoom;

/**
 * Représente une entrée utilisée pour maintenir la liste des pièces visitées / fermées. Les objets de type Entry
 * sont non mutables.
 * @inv getRoom() != null
 */
public class Entry {
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

    @Override
    public int hashCode() {
        return (getRoom().hashCode() << getX()) >> getY();
    }
}