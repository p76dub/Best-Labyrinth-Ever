package model.interfaces;

public interface IPlayer extends IEntity {

    /**
     * Prendre un item.
     * @param item
     */
    void take(IItem item);
}