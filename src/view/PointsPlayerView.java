package view;

import model.EntityPositionKeeper;
import model.Maze;
import model.Player;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PointsPlayerView extends JPanel {

    // ATTRIBUTS
    private IPlayer player;
    private JPanel panelLife;
    private JPanel panelAttack;
    private JPanel panelDefensive;

    // CONSTRUCTEUR
    public PointsPlayerView(IPlayer player) {
        createModel(player);
        createView();
        placeComponents();
        createController();
    }

    //REQUETES
    public IPlayer getPlayer() {
        return player;
    }

    // OUTILS
    private void createModel(IPlayer player) {
        this.player = player;
    }

    private void createView() {
        panelLife = new JPanel();
        panelAttack= new JPanel();
        panelDefensive = new JPanel();
    }

    private void placeComponents() {
        //alignement des composants verticalement
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        //ajout des points de vie
        JPanel s = new JPanel(); {
            JLabel life = new JLabel("Points vie : ");
            s.add(life);
            panelLife = new JPanel(new GridLayout(1, getPlayer().getLifePoints())); {
                for (int i = 0; i < getPlayer().getLifePoints(); i++) {
                    ImageIcon icon = new ImageIcon("images/coeur.png");
                    Image img = icon.getImage();
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    JLabel imageDefensive = new JLabel(new ImageIcon(img));
                    panelDefensive.add(imageDefensive);
                }
            }
            s.add(panelDefensive);
        }
        listPane.add(s);

        this.add(listPane);
    }

    private void createController() {
        getPlayer().addPropertyChangeListener(IPlayer.LIFE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelLife.removeAll();
                    JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                        for (int i = 0; i < (int) evt.getNewValue(); i++) {
                            ImageIcon icon = new ImageIcon("images/coeur.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel imageLife = new JLabel(new ImageIcon(img));
                            p.add(imageLife);
                        }
                        panelLife.add(p);
                    }
                    panelLife.revalidate();
                }
            }
        );


        getPlayer().addPropertyChangeListener(IPlayer.ATTACK_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelAttack.removeAll();
                    JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                        for (int i = 0; i < (int) evt.getNewValue(); i++) {
                            ImageIcon icon = new ImageIcon("images/epee.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel imageAttack = new JLabel(new ImageIcon(img));
                            p.add(imageAttack);
                        }
                        panelAttack.add(p);
                    }
                    panelAttack.revalidate();
                }
            }
        );

        getPlayer().addPropertyChangeListener(IPlayer.DEFENSE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelDefensive.removeAll();
                    JPanel p = new JPanel(new GridLayout(1, (int) evt.getNewValue())); {
                        for (int i = 0; i < (int) evt.getNewValue(); i++) {
                            ImageIcon icon = new ImageIcon("images/bouclier.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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

    // TEST
    public static void main(String[] args) {
        class Bla {
            private static final String filename = "Points de vie";
            JFrame mainFrame = new JFrame(filename);
            IPlayer player;
            public final int MAX_INITIAL_ATTACK_POINTS = 20;
            public final int MAX_INITIAL_DEFENSIVE_POINTS = 20;
            public final int MAX_INITIAL_LIVE_POINTS = 20;
            public final int MIN_INITIAL_LIVE_POINTS = 5;

            public Bla() {
                Random random = new Random();
                int attackPoints = random.nextInt(MAX_INITIAL_ATTACK_POINTS) + 1;
                int defensivePoints = random.nextInt(MAX_INITIAL_DEFENSIVE_POINTS) + 1;
                int livePoints =  random.nextInt(MAX_INITIAL_LIVE_POINTS - MIN_INITIAL_LIVE_POINTS) + MIN_INITIAL_LIVE_POINTS;
                //TODO à changer disparaitre room & maze
                IMaze maze = null;
                try {
                    maze = new Maze();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println(attackPoints +"/"+defensivePoints+"/"+livePoints);
                Map<Direction, URI> map = new HashMap<>();
                map.put(Direction.EAST, URI.create("images/player.png"));
                map.put(Direction.WEST, URI.create("images/player_left.png"));
                map.put(Direction.NORTH, URI.create("images/player_top.png"));
                map.put(Direction.SOUTH, URI.create("images/player_bottom.png"));
                player = new Player("test", attackPoints, defensivePoints, livePoints, map);
                EntityPositionKeeper.getInstance().registerEntity(player, maze.getRooms()[0][0]);
                mainFrame.add(new PointsPlayerView(player), BorderLayout.CENTER);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            public void display() {
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Bla().display();
            }

        });
    }
}
