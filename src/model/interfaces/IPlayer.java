package model.interfaces;

public interface IPlayer extends IEntity {

    // STATICS
    String POSITION_PROPERTY = "position";
    String TAKE_PROPERTY = "take";
    String DEFENSE_PROPERTY = "defense";
    String ATTACK_PROPERTY = "attack";
    String LIFE_PROPERTY = "life";

    /**
     * Prendre un item.
     * @param item
     */
    void take(IItem item);
}