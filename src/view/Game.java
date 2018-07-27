package view;

import model.GrowingTreeGenerator;
import model.Maze;
import model.Player;
import model.Item;
import model.interfaces.IItem;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import model.interfaces.IRoom;
import util.Direction;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Paths;

public class Game {

    // ATTRIBUTS
    private JFrame mainFrame;
    private JPanel panelAttack;
    private JPanel panelDefensive;
    private JPanel panelLife;
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
        try {
            maze = new Maze();
            getMaze().build(GrowingTreeGenerator.class);
            player = new Player("player1", 5, 2, 10, maze.getRooms()[0][0]);
            xItem = (int) (Math.random() * (getMaze().colsNb()));
            yItem = (int) (Math.random() * (getMaze().rowsNb()));
            IItem it = new Item("Un joli bonbon !\n Tu gagnes 5 points d'attaque, " +
                    "3 points de défense.\n Tu perds 2 points de vie.", Paths.get(""),
                    5, 3, -2, maze.getRooms()[xItem][yItem]);
            getMaze().getRooms()[xItem][yItem].setItem(it);
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


        panelLife = new JPanel();
        panelAttack= new JPanel();
        panelDefensive = new JPanel();
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
                panelLife = new JPanel(new GridLayout(1, getPlayer().getLifePoints())); {
                    for (int i = 0; i < getPlayer().getLifePoints(); i++) {
                        ImageIcon icon = new ImageIcon("images/coeur.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageLife = new JLabel(new ImageIcon(img));
                        panelLife.add(imageLife);
                    }
                }
                s.add(panelLife);
            }
            listPane.add(s);

            //ajout des points d'attaque
            s = new JPanel(); {
                JLabel attack = new JLabel("Points d'attaque : ");
                s.add(attack);
                panelAttack = new JPanel(new GridLayout(1, getPlayer().getAttackPoints())); {
                    for (int i = 0; i < getPlayer().getAttackPoints(); i++) {
                        ImageIcon icon = new ImageIcon("images/epee.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageAttack = new JLabel(new ImageIcon(img));
                        panelAttack.add(imageAttack);
                    }
                }
                s.add(panelAttack);
            }
            listPane.add(s);

            //ajout des points de défense
            s = new JPanel(); {
                JLabel defensive = new JLabel("Points de défense : ");
                s.add(defensive);
                panelDefensive = new JPanel(new GridLayout(1, getPlayer().getDefensivePoints())); {
                    for (int i = 0; i < getPlayer().getDefensivePoints(); i++) {
                        ImageIcon icon = new ImageIcon("images/bouclier.png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                        JLabel imageDefensive = new JLabel(new ImageIcon(img));
                        panelDefensive.add(imageDefensive);
                    }
                }
                s.add(panelDefensive);
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

        getPlayer().addPropertyChangeListener("CHANGE_LIFE",
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelLife.removeAll();
                    JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                        for (int i = 0; i < (int) evt.getNewValue(); i++) {
                            ImageIcon icon = new ImageIcon("images/coeur.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                            JLabel imageLife = new JLabel(new ImageIcon(img));
                            p.add(imageLife);
                        }
                        panelLife.add(p);
                    }
                    panelLife.revalidate();
                }
            }
        );

        getPlayer().addPropertyChangeListener("CHANGE_ATTACK",
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        panelAttack.removeAll();
                        JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                            for (int i = 0; i < (int) evt.getNewValue(); i++) {
                                ImageIcon icon = new ImageIcon("images/epee.png");
                                Image img = icon.getImage();
                                img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                                JLabel imageAttack = new JLabel(new ImageIcon(img));
                                p.add(imageAttack);
                            }
                            panelAttack.add(p);
                        }
                        panelAttack.revalidate();
                    }
                }
        );

        getPlayer().addPropertyChangeListener("CHANGE_DEFENSIVE",
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelDefensive.removeAll();
                    JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                        for (int i = 0; i < (int) evt.getNewValue(); i++) {
                            ImageIcon icon = new ImageIcon("images/bouclier.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                            JLabel imageDefensive = new JLabel(new ImageIcon(img));
                            p.add(imageDefensive);
                        }
                        panelDefensive.add(p);
                    }
                    panelDefensive.revalidate();
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
