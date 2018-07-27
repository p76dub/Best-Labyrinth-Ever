package view;

import model.generators.GrowingTreeGenerator;
import model.Maze;
import model.Player;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import util.Direction;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.Component.LEFT_ALIGNMENT;

public class Game {

    // ATTRIBUTS
    private JFrame mainFrame;
    private IPlayer player;
    private MazeView mazeView;
    private IMaze maze;

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
        try {
            maze = new Maze();
            maze.build(GrowingTreeGenerator.class);
            player = new Player("player1", 5, 2, 10, maze.getRooms()[0][0]);
        } catch (IMaze.MazeGeneratorCreationException e) {
            e.printStackTrace();
        }
    }

    private void createView() {
        final int frameWidth = 600;
        final int frameHeight = 600;

        mainFrame = new JFrame("Labyrinthe");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeView = new MazeView(maze);
        //mazeView.placePlayer(0,0);
    }

    private void placeComponents() {
        mainFrame.setLayout(new BorderLayout()); {

            //alignement des composants verticalement
            JPanel listPane = new JPanel();
            listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

            //ajout du composant labyrinthe
            mazeView.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            listPane.add(mazeView);

            //ajout d'un espèce
            listPane.add(new JPanel());

            //ajout des points de vie
            JPanel s = new JPanel(); {
                JLabel life = new JLabel("Points vie : ");
                s.add(life);
                JPanel t = new JPanel(new GridLayout(1, getPlayer().getLifePoints())); {
                    for (int i = 0; i < getPlayer().getLifePoints(); i++) {
                        ImageIcon icon = new ImageIcon("images/coeur.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageLife = new JLabel(new ImageIcon(img));
                        t.add(imageLife);
                    }
                }
                s.add(t);
            }
            listPane.add(s);

            //ajout des points d'attaque
            s = new JPanel(); {
                JLabel attack = new JLabel("Points d'attaque : ");
                s.add(attack);
                JPanel t = new JPanel(new GridLayout(1, getPlayer().getAttackPoints())); {
                    for (int i = 0; i < getPlayer().getAttackPoints(); i++) {
                        ImageIcon icon = new ImageIcon("images/epee.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageAttack = new JLabel(new ImageIcon(img));
                        t.add(imageAttack);
                    }
                }
                s.add(t);
            }
            listPane.add(s);

            //ajout des points de défense
            s = new JPanel(); {
                JLabel defensive = new JLabel("Points de défense : ");
                s.add(defensive);
                JPanel t = new JPanel(new GridLayout(1, getPlayer().getDefensivePoints())); {
                    for (int i = 0; i < getPlayer().getDefensivePoints(); i++) {
                        /*
                        ImageIcon icon = new ImageIcon("images/bouclier.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageDefensive = new JLabel(new ImageIcon(img));
                        t.add(imageDefensive);
                        */
                        t.add(new JLabel("B"));
                    }
                }
                s.add(t);
            }
            listPane.add(s);

            mainFrame.add(listPane);
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
    }

    private void bottom() {
        if (getPlayer().getRoom().canExitIn(Direction.SOUTH)) {
            getPlayer().move(Direction.SOUTH);
            System.out.println("bas");
        }
        System.out.println("essai bas");
    }

    private void left() {
        if (getPlayer().getRoom().canExitIn(Direction.WEST)) {
            getPlayer().move(Direction.WEST);
            System.out.println("gauche");
        }
        System.out.println("essai gauche");
    }

    private void right() {
        if (getPlayer().getRoom().canExitIn(Direction.EAST)) {
            getPlayer().move(Direction.EAST);
            System.out.println("droite");
        }
        System.out.println("essai droite");
    }

    private void top() {
        if (getPlayer().getRoom().canExitIn(Direction.NORTH)) {
            getPlayer().move(Direction.NORTH);
            System.out.println("haut");
        }
        System.out.println("essai haut");
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
