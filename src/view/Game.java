package view;

import model.*;
import model.enemies.EnemyFactory;
import model.interfaces.*;
import model.maze.IMazeGenerator;
import model.maze.MazeFactory;
import util.Direction;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

public class Game {

    //CONSTANTES
    final int MAX_INITIAL_ATTACK_POINTS = 10;
    final int MAX_INITIAL_DEFENSIVE_POINTS = 10;
    final int MAX_INITIAL_LIVE_POINTS = 10;
    final int MIN_INITIAL_LIVE_POINTS = 5;

    // ATTRIBUTS
    private JFrame mainFrame;
    private PointsPlayerView pointsPlayer;
    private MazeView mazeView;
    private GameModel model;
    private CaptionView captionView;
    private boolean resume;


    // CONSTRUCTEUR
    public Game(String name) {
        try {
            createModel(name);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        createView();
        placeComponents();
        createController();
        this.model.start();
        this.model.freezeEnemies();
    }

    // REQUETES
    public IMaze getMaze() { return model.getMaze(); }

    public IPlayer getPlayer() {
        return model.getPlayer();
    }

    public GameModel getModel() { return model; }

    // COMMANDES
    public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // OUTILS
    private void createModel(String name) throws URISyntaxException {
        IMaze maze = MazeFactory.backTrackingGenerator(10, 10);

        IPlayer player = generatorPlayer(name);

        Collection< IEnemy > enemies = new ArrayList<>();
        enemies.add(EnemyFactory.createZombie());
        enemies.add(EnemyFactory.createZombie2());
        //enemies.add(EnemyFactory.createZombie3());

        Collection<IItem> items = generatorItems(3);

        model = new GameModel(maze, player, enemies, items);
    }

    private void createView() {
        mainFrame = new JFrame("Labyrinthe");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mazeView = new MazeView(getMaze());

        pointsPlayer = new PointsPlayerView(getPlayer());

        captionView = new CaptionView(model);
    }

    private void placeComponents() {
        JPanel m = new JPanel(new GridBagLayout()); {
            m.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            gbc.gridwidth = 1;

            //ajout du composant labyrinthe
            mazeView.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            m.add(mazeView, gbc);

            gbc.gridheight = 1;
            gbc.gridx = 1;
            m.add(captionView, gbc);

            gbc.gridy = 1;
            m.add(pointsPlayer, gbc);
        }
        mainFrame.add(m);
    }

    private void createController() {
        // système d'écouteurs pour les raccouris clavier
        mainFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                // flèche du bas
                if (code == KeyEvent.VK_DOWN) {
                    bottom();
                }

                // flèche de droite
                if (code == KeyEvent.VK_RIGHT) {
                    right();
                }

                // flèche de gauche
                if (code == KeyEvent.VK_LEFT) {
                    left();
                }

                // flèche du haut
                if (code == KeyEvent.VK_UP) {
                    top();
                }

                if (!resume) {
                    model.resumeEnemies();
                    resume = true;
                }
            }
        });

        getPlayer().addPropertyChangeListener(IPlayer.TAKE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            getModel().freezeEnemies();
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    ((IItem) evt.getNewValue()).getMessage(),
                                    "Objet trouvé",
                                    JOptionPane.PLAIN_MESSAGE
                            );
                            getModel().resumeEnemies();
                        }
                    });
                }
            }
        );

        getPlayer().addPropertyChangeListener(IPlayer.POSITION_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!getPlayer().isDead() && evt.getNewValue().equals(getMaze().exit()) &&
                                        getMaze().getPrincess().isSafe()) {
                                    getModel().stopEnemies();
                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Félicitation " + getPlayer().getName() + ", tu as gagné !",
                                            "Victoire",
                                            JOptionPane.PLAIN_MESSAGE
                                    );
                                }
                            }
                        });
                    }
                });

        getPlayer().addPropertyChangeListener(IPlayer.POSITION_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getNewValue().equals(getMaze().getPrincess().getRoom())
                        && !getMaze().getPrincess().isSafe()) {
                            getMaze().getPrincess().save();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    getModel().freezeEnemies();
                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            getMaze().getPrincess().getMessage(),
                                            "Princesse sauvée",
                                            JOptionPane.PLAIN_MESSAGE
                                    );
                                    getModel().resumeEnemies();
                                }
                            });
                        }

                    }
                }
        );


        getPlayer().addPropertyChangeListener(IPlayer.DEAD_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i< getModel().getEnemies().size(); i++) {
                                        getModel().getEnemies().get(i).stop();
                                    }
                                    JOptionPane.showMessageDialog(
                                            mainFrame,
                                            "Vous êtes mort !",
                                            "Game Over",
                                            JOptionPane.PLAIN_MESSAGE
                                    );
                                }
                            });
                        }

                    }
        );

        getModel().addPropertyChangeListener(IPlayer.KILL_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                getModel().stopEnemies();
                                JOptionPane.showMessageDialog(
                                        mainFrame,
                                        "Vous avez été tué par "+ ((IEntity) evt.getNewValue()).getName(),
                                        "Game Over",
                                        JOptionPane.PLAIN_MESSAGE
                                );
                            }
                        });
                    }

                }
        );
    }

    private void bottom() {
        if (!getPlayer().isDead() && getPlayer().getRoom().canExitIn(Direction.SOUTH)) {
            getPlayer().move(Direction.SOUTH);
        }
    }

    private void left() {
        if (!getPlayer().isDead() && getPlayer().getRoom().canExitIn(Direction.WEST)) {
            getPlayer().move(Direction.WEST);
        }
    }

    private void right() {
        if (!getPlayer().isDead() && getPlayer().getRoom().canExitIn(Direction.EAST)) {
            getPlayer().move(Direction.EAST);
        }
    }

    private void top() {
        if (!getPlayer().isDead() && getPlayer().getRoom().canExitIn(Direction.NORTH)) {
            getPlayer().move(Direction.NORTH);
        }
    }

    private IPlayer generatorPlayer(String name) {
        Random random = new Random();
        int attackPoints = random.nextInt(MAX_INITIAL_ATTACK_POINTS) + 1;
        int defensivePoints = random.nextInt(MAX_INITIAL_DEFENSIVE_POINTS) + 1;
        int livePoints =  random.nextInt(MAX_INITIAL_LIVE_POINTS - MIN_INITIAL_LIVE_POINTS) + MIN_INITIAL_LIVE_POINTS;

        Map<Direction, URI> map = new HashMap<>();
        try {
            map.put(Direction.EAST, getClass().getResource("../player.png").toURI());
            map.put(Direction.WEST, getClass().getResource("../player_left.png").toURI());
            map.put(Direction.NORTH, getClass().getResource("../player_top.png").toURI());
            map.put(Direction.SOUTH, getClass().getResource("../player_bottom.png").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new Player(name, attackPoints, defensivePoints, livePoints, map);
    }

    private Collection<IItem> generatorItems(int number) {
        Collection<IItem> items = new ArrayList<>();
        Random random = new Random();
        for(int i = 1; i <= number; i++) {
            int attackPoints = random.nextInt(IItem.MAX_ITEM_ATTACK_POINTS - IItem.MIN_ITEM_ATTACK_POINTS
                    + 1) + IItem.MIN_ITEM_ATTACK_POINTS;
            int defensivePoints = random.nextInt(IItem.MAX_ITEM_DEFENSIVE_POINTS
                    - IItem.MIN_ITEM_DEFENSIVE_POINTS + 1) + IItem.MIN_ITEM_DEFENSIVE_POINTS;
            int livePoints =  random.nextInt(IItem.MAX_ITEM_LIVE_POINTS - IItem.MIN_ITEM_LIVE_POINTS
                    + 1) + IItem.MIN_ITEM_LIVE_POINTS;
            String message = "Points de vie : " + livePoints + "\n"
                            + "Points d'attaque : " + attackPoints + "\n"
                            + "Points de défense : " + defensivePoints + "\n";
            items.add(new Item(message, Paths.get("images/bonbon.png"),
                    attackPoints, defensivePoints, livePoints));
        }
        return items;
    }

    // LANCEUR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Game("test").display();
            }
        });
    }
}
