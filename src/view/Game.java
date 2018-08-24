package view;

import model.*;
import model.generators.GeneratorFactory;
import model.interfaces.IEnemy;
import model.interfaces.IItem;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Game {

    //CONSTANTES
    final int MAX_INITIAL_ATTACK_POINTS = 10;
    final int MAX_INITIAL_DEFENSIVE_POINTS = 10;
    final int MAX_INITIAL_LIVE_POINTS = 10;
    final int MIN_INITIAL_LIVE_POINTS = 5;

    final int MAX_ITEM_ATTACK_POINTS = 5;
    final int MIN_ITEM_ATTACK_POINTS = -5;
    final int MAX_ITEM_DEFENSIVE_POINTS = 5;
    final int MIN_ITEM_DEFENSIVE_POINTS = -5;
    final int MAX_ITEM_LIVE_POINTS = 5;
    final int MIN_ITEM_LIVE_POINTS = -5;

    // ATTRIBUTS
    private JFrame mainFrame;
    private PointsPlayerView pointsPlayer;
    private MazeView mazeView;
    private GameModel model;


    // CONSTRUCTEUR
    public Game() {
        try {
            createModel();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        createView();
        placeComponents();
        createController();
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
    private void createModel() throws URISyntaxException {
        IMaze maze = new Maze();
        GeneratorFactory.backTrackingGenerator(maze);

        IPlayer player = generatorPlayer("test");

        Collection< IEnemy > enemies = new ArrayList<>();

        Collection<IItem> items = generatorItems(3);

        model = new GameModel(maze, player, enemies, items);
    }

    private void createView() {
        final int frameWidth = 600;
        final int frameHeight = 600;

        mainFrame = new JFrame("Labyrinthe");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mazeView = new MazeView(getMaze());

        pointsPlayer = new PointsPlayerView(getPlayer());
    }

    private void placeComponents() {
        mainFrame.setLayout(new BorderLayout()); {

            //ajout du composant labyrinthe
            mazeView.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            mainFrame.add(mazeView, BorderLayout.NORTH);

            //ajout les points du joueur
            mainFrame.add(pointsPlayer, BorderLayout.SOUTH);
        }
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
            }
        });
        //TODO A modifier
        /*
        getMaze().getRooms()[xItem][yItem].addPropertyChangeListener("TAKE",
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    JOptionPane.showMessageDialog(mainFrame,
                            ((IItem) evt.getOldValue()).getMessage(),
                            "Objet trouvé",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        );
        */
    }

    private void bottom() {
        if (getPlayer().getRoom().canExitIn(Direction.SOUTH)) {
            getPlayer().move(Direction.SOUTH);
        }
    }

    private void left() {
        if (getPlayer().getRoom().canExitIn(Direction.WEST)) {
            getPlayer().move(Direction.WEST);
        }
    }

    private void right() {
        if (getPlayer().getRoom().canExitIn(Direction.EAST)) {
            getPlayer().move(Direction.EAST);
        }
    }

    private void top() {
        if (getPlayer().getRoom().canExitIn(Direction.NORTH)) {
            getPlayer().move(Direction.NORTH);
        }
    }

    private IPlayer generatorPlayer(String name) {
        Random random = new Random();
        int attackPoints = random.nextInt(MAX_INITIAL_ATTACK_POINTS) + 1;
        int defensivePoints = random.nextInt(MAX_INITIAL_DEFENSIVE_POINTS) + 1;
        int livePoints =  random.nextInt(MAX_INITIAL_LIVE_POINTS - MIN_INITIAL_LIVE_POINTS) + MIN_INITIAL_LIVE_POINTS;

        Map<Direction, URI> map = new HashMap<>();
        map.put(Direction.EAST, URI.create("images/player.png"));
        map.put(Direction.WEST, URI.create("images/player_left.png"));
        map.put(Direction.NORTH, URI.create("images/player_top.png"));
        map.put(Direction.SOUTH, URI.create("images/player_bottom.png"));
        return new Player(name, attackPoints, defensivePoints, livePoints, map);
    }

    private Collection<IItem> generatorItems(int number) {
        Collection<IItem> items = new ArrayList<>();
        Random random = new Random();
        for(int i = 1; i <= number; i++) {
            int attackPoints = random.nextInt(MAX_ITEM_ATTACK_POINTS - MIN_ITEM_ATTACK_POINTS
                    + 1) + MIN_ITEM_ATTACK_POINTS;
            int defensivePoints = random.nextInt(MAX_ITEM_DEFENSIVE_POINTS
                    - MIN_ITEM_DEFENSIVE_POINTS + 1) + MIN_ITEM_DEFENSIVE_POINTS;
            int livePoints =  random.nextInt(MAX_ITEM_LIVE_POINTS - MIN_ITEM_LIVE_POINTS
                    + 1) + MIN_ITEM_LIVE_POINTS;
            items.add(new Item("bonbon", Paths.get("images/bonbon.png"),
                    attackPoints, defensivePoints, livePoints));
        }
        return items;
    }

    // LANCEUR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Game().display();
            }
        });
    }
}
