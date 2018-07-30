package model;

import model.interfaces.*;

import java.util.List;

public class GameModel {
    private IMaze maze;
    private IPlayer player;
    private List<IEnemy> enemies;
    private List<IItem> items;
    private IPrincess princess;

    public GameModel() {}

    public void setPlayer(IPlayer player) {
        if (player == null) {
            throw new NullPointerException();
        }
        this.player = player;
    }

    public void setPlayer(String name) {
        this.player = new Player()
    }
}
