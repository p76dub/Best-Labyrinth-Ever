package view;

import model.generators.GeneratorFactory;
import model.Maze;
import model.Player;
import model.Item;
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
import java.nio.file.Paths;
import java.util.Random;

public class Game {

    // ATTRIBUTS
    private JFrame mainFrame;
    private PointsPlayerView pointsPlayer;
    private IPlayer player;
    private MazeView mazeView;
    private IMaze maze;
    private int xItem;
    private int yItem;


    // CONSTRUCTEUR
    public Game() {
        createModel();
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    public IMaze getMaze() {
        return maze;
    }
    public IPlayer getPlayer() {
        return player;
    }

    // COMMANDES
    public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // OUTILS
    private void createModel() {
        final int MAX_INITIAL_ATTACK_POINTS = 20;
        final int MAX_INITIAL_DEFENSIVE_POINTS = 20;
        final int MAX_INITIAL_LIVE_POINTS = 20;
        final int MIN_INITIAL_LIVE_POINTS = 5;

        maze = new Maze();
        GeneratorFactory.backTrackingGenerator(maze);
        Random random = new Random();
        int attackPoints = random.nextInt(MAX_INITIAL_ATTACK_POINTS) + 1;
        int defensivePoints = random.nextInt(MAX_INITIAL_DEFENSIVE_POINTS) + 1;
        int livePoints =  random.nextInt(MAX_INITIAL_LIVE_POINTS - MIN_INITIAL_LIVE_POINTS) + MIN_INITIAL_LIVE_POINTS;

        //TODO à changer
        player = new Player("test", attackPoints, defensivePoints, livePoints,  maze.getRooms()[0][0]);
        xItem = (int) (Math.random() * (getMaze().colsNb()));
        yItem = (int) (Math.random() * (getMaze().rowsNb()));
        IItem it = new Item("Un joli bonbon !\n Tu gagnes 5 points d'attaque, " +
                "3 points de défense.\n Tu perds 2 points de vie.", Paths.get(""),
                5, 3, -2, maze.getRooms()[xItem][yItem]);
        getMaze().getRooms()[xItem][yItem].setItem(it);
    }

    private void createView() {
        final int frameWidth = 600;
        final int frameHeight = 600;

        mainFrame = new JFrame("Labyrinthe");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeView = new MazeView(maze);


        pointsPlayer = new PointsPlayerView(getPlayer());
        //mazeView.placePlayer(0,0);
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
        getMaze().getRooms()[xItem][yItem].addPropertyChangeListener("TAKE",
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    JOptionPane.showMessageDialog(mainFrame,
                            ((IItem) evt.getOldValue()).getMessage(),
                            "Bonus",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        );
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

    // LANCEUR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Game().display();
            }
        });
    }
}
