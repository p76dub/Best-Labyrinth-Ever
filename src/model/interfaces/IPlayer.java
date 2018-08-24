package model.interfaces;

import java.beans.PropertyChangeListener;

public interface IPlayer extends IEntity {

    /**
     * Prendre un item.
     * @param item
     */
    void take(IItem item);

    //TODO à changer
    void addPropertyChangeListener(String property, PropertyChangeListener l);
    void removePropertyChangeListener(PropertyChangeListener l);

}