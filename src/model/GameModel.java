package model;

import model.interfaces.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GameModel {

    // ATTRIBUTS
    private IMaze maze;
    private IPlayer player;
    private List<IEnemy> enemies;
    private List<IItem> items;

    // CONSTRUCTEUR
    public GameModel(IMaze maze, IPlayer player, Collection<IEnemy> enemies, Collection<IItem> items) {
        if (maze == null || player == null || enemies == null || items == null) {
            throw new NullPointerException();
        }
        if (maze.colsNb() * maze.rowsNb() < 4) {  // Au moins 3 pièces : entrée, sortie et princesse
            throw new IllegalArgumentException();
        }
        if (enemies.size() > maze.colsNb() * maze.rowsNb() - 2) { // Pas d'ennemis sur l'entrée ou la sortie
            throw new IllegalArgumentException();
        }
        if (items.size() > maze.colsNb() * maze.rowsNb() - 3) { // Pas d'items sur l'entrée, la sortie et la princesse
            throw new IllegalArgumentException();
        }
        this.maze = maze;
        this.player = player;

        this.enemies = new ArrayList<>(enemies);
        setEnemies(this.enemies);

        this.items = new ArrayList<>(items);
        setItems(this.items);
    }

    // REQUÊTES
    public IPlayer getPlayer() { return player; }

    public List<IEnemy> getEnemies() { return new ArrayList<>(enemies); }

    public List<IItem> getItems() { return new ArrayList<>(items); }

    public IPrincess getPrincess() { return maze.getPrincess(); }

    public IMaze getMaze() { return maze; }

    // OUTILS
    private void setItem(IItem item) {
        assert  item != null;
        IRoom[][] rooms = this.maze.getRooms();
        boolean positioned = false;
        Random r = new Random();

        while (!positioned) {
            IRoom room = rooms[r.nextInt(rooms.length)][r.nextInt(rooms[0].length)];
            if (room.getItem() == null && !maze.entry().equals(room) && !maze.exit().equals(room)
                    && !getPrincess().getRoom().equals(room)) {
                room.setItem(item);
                positioned = true;
            }
        }
    }

    private void setItems(List<IItem> items) {
        assert items != null;
        for (IItem i : items) {
            this.setItem(i);
        }
    }

    private void setEnemy(IEnemy enemy) {
        assert enemy != null;
        IRoom[][] rooms = this.maze.getRooms();
        boolean positioned = false;
        Random r = new Random();

        while (!positioned) {
            IRoom room = rooms[r.nextInt(rooms.length)][r.nextInt(rooms[0].length)];
            if (!maze.entry().equals(room) && !maze.exit().equals(room)) {
                EntityPositionKeeper.getInstance().registerEntity(enemy, room);
                positioned = true;
            }
        }
    }

    private void setEnemies(List<IEnemy> enemies) {
        assert enemies != null;
        for (IEnemy e : enemies) {
            this.setEnemy(e);
        }
    }

}
