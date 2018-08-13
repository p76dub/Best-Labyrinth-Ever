package model;

import model.interfaces.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    //CONSTANTES
    public final int MAX_INITIAL_ATTACK_POINTS = 20;
    public final int MAX_INITIAL_DEFENSIVE_POINTS = 20;
    public final int MAX_INITIAL_LIVE_POINTS = 20;
    public final int MIN_INITIAL_LIVE_POINTS = 5;

    public final int MAX_ITEM_ATTACK_POINTS = 5;
    public final int MAX_ITEM_DEFENSIVE_POINTS = 5;
    public final int MAX_ITEM_LIVE_POINTS = 10;

    //ATTRIBUTS
    private IMaze maze;
    private IPlayer player;
    private List<IEnemy> enemies;
    private List<IItem> items;
    private IPrincess princess;

    //CONSTRUCTEUR
    public GameModel() {
        enemies = new ArrayList<IEnemy>();
        items = new ArrayList<IItem>();
    }

    //REQUÊTES
    public IPlayer getPlayer() { return player; }

    public List<IEnemy> getEnemies() { return enemies; }

    public List<IItem> getItems() { return items; }

    public IPrincess getPrincess() { return princess; }

    public IMaze getMaze() { return maze; }

    //COMMANDES
    public void setPlayer(IPlayer player) {
        if (player == null) {
            throw new NullPointerException();
        }
        this.player = player;
    }

    public void setPlayer(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        int attackPoints = (int) (Math.random() * (MAX_INITIAL_ATTACK_POINTS + 1));
        int defensivePoints = (int) (Math.random() * (MAX_INITIAL_DEFENSIVE_POINTS + 1));
        int livePoints = (int) (Math.random() * (MAX_INITIAL_LIVE_POINTS - MIN_INITIAL_LIVE_POINTS + 1) + MIN_INITIAL_LIVE_POINTS);
        this.player = new Player(name, attackPoints, defensivePoints, livePoints);
    }

    public void setMaze(IMaze maze) {
        if (maze == null) {
            throw new NullPointerException();
        }
        this.maze = maze;
    }

    public void setMaze(int width, int height) {
        this.maze = new Maze(width, height);
    }

    public void setMaze() {
        this.maze = new Maze();
    }

    public void setItems(List<IItem> items) {
        if (items == null) {
            throw new NullPointerException();
        }
        this.items = items;
    }

    public void addItem(IItem item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items.add(item);
    }

    public void addItem(String message, Path pathname) {
        if (message == null || pathname == null) {
            throw new NullPointerException();
        }
        int attackPoints = (int) (Math.random() * (MAX_ITEM_ATTACK_POINTS + 1));
        int defensivePoints = (int) (Math.random() * (MAX_ITEM_DEFENSIVE_POINTS + 1));
        int livePoints = (int) (Math.random() * (MAX_ITEM_LIVE_POINTS + 1));
        IItem item = new Item(message, pathname,  attackPoints, defensivePoints, livePoints, null);
        items.add(item);
    }

    public void removeItem(IItem item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items.remove(item);
    }
}
