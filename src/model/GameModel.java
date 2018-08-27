package model;

import model.interfaces.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

        EntityPositionKeeper keeper = EntityPositionKeeper.getInstance();
        keeper.registerEntity(player, maze.entry());

        this.enemies = new ArrayList<>(enemies);
        setEnemies(this.enemies);

        this.items = new ArrayList<>(items);
        setItems(this.items);

        this.player.addPropertyChangeListener(IPlayer.POSITION_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Collection<IEntity> entities = keeper.getEntities((IRoom) evt.getNewValue());
                if (entities.size() > 1) {
                    // First, freeze all enemies
                    freezeEnemies();

                    // Then, resolve combat(s)
                    for (IEntity entity : entities) {
                        if (!entity.equals(GameModel.this.player)) {
                            executeCombat(GameModel.this.player, entity);
                        }
                    }
                    // Delete dead entities
                    stopAndRemoveDeadEnemies();

                    // Finally, resume all enemies
                    resumeEnemies();
                }
            }
        });
    }

    // REQUÊTES
    public IPlayer getPlayer() { return player; }

    public List<IEnemy> getEnemies() { return new ArrayList<>(enemies); }

    public List<IItem> getItems() { return new ArrayList<>(items); }

    public IPrincess getPrincess() { return maze.getPrincess(); }

    public IMaze getMaze() { return maze; }

    //COMMANDES
    public void start() {
        for (IEnemy enemy : this.enemies) {
            enemy.start();
        }
    }

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

        enemy.addPropertyChangeListener(IEnemy.POSITION_PROPERTY, evt -> {
            EntityPositionKeeper keeper = EntityPositionKeeper.getInstance();
            Collection<IEntity> entities = keeper.getEntities((IRoom) evt.getNewValue());
            if (entities.contains(GameModel.this.player)) {
                freezeEnemies();
                executeCombat(enemy, GameModel.this.player);
                stopAndRemoveDeadEnemies();
                resumeEnemies();
            }
        });

    }

    private void setEnemies(List<IEnemy> enemies) {
        assert enemies != null;
        for (IEnemy e : enemies) {
            this.setEnemy(e);
        }
    }

    private void freezeEnemies() {
        for (IEnemy enemy : enemies) {
            enemy.pause();
        }
    }

    private void resumeEnemies() {
        for (IEnemy enemy : enemies) {
            enemy.resume();
        }
    }

    private void stopAndRemoveDeadEnemies() {
        List<IEnemy> deleted = new ArrayList<>();
        for (IEnemy enemy : enemies) {
            if (enemy.isDead()) {
                deleted.add(enemy);
                EntityPositionKeeper.getInstance().deleteEntity(enemy);
                enemy.stop();
            }
        }
        this.enemies.removeAll(deleted);
    }

    private void executeCombat(IEntity attacker, IEntity defender) {
        while (!attacker.isDead() && !defender.isDead()) {
            attacker.attack(defender);
            if (!defender.isDead()) {
                defender.attack(attacker);
            }
        }
    }
}
