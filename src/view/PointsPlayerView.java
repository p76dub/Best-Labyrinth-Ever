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
        this.setLayout(new GridBagLayout()); {

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;

            JPanel s = new JPanel(new FlowLayout()); {
                JLabel life = new JLabel("Mes points de vie :");
                s.add(life);

                //ajout des points de vie
                JPanel p = new JPanel(new FlowLayout()); {
                    p.add(imageLife());
                    JLabel caption = new JLabel("x" + getPlayer().getLifePoints());
                    p.add(caption);
                }
                panelLife.add(p);
                s.add(panelLife);
            }
            gbc.gridx = 0;
            gbc.gridy += 1;
            this.add(s, gbc);

            s = new JPanel(new FlowLayout()); {
                JLabel life = new JLabel("Mes points d'attaque:");
                s.add(life);

                //ajout des points d'attaque
                JPanel p = new JPanel(new FlowLayout()); {
                    p.add(imageAttack());
                    JLabel caption = new JLabel("x" + getPlayer().getAttackPoints());
                    p.add(caption);
                }
                panelAttack.add(p);
                s.add(panelAttack);
            }
            gbc.gridx = 0;
            gbc.gridy += 1;
            this.add(s, gbc);

            s = new JPanel(new FlowLayout()); {
                JLabel life = new JLabel("Mes points de défense:");
                s.add(life);

                //ajout des points de défense
                JPanel p = new JPanel(new FlowLayout()); {
                    p.add(imageDefensive());
                    JLabel caption = new JLabel("x" + getPlayer().getDefensivePoints());
                    p.add(caption);
                }
                panelDefensive.add(p);
                s.add(panelDefensive);
            }
            gbc.gridy += 1;
            this.add(s, gbc);
        }
    }

    private void createController() {
        getPlayer().addPropertyChangeListener(IPlayer.LIFE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelLife.removeAll();
                    JPanel p = new JPanel(new FlowLayout()); {
                        p.add(imageLife());
                        JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                        p.add(caption);
                    }
                    panelLife.add(p);
                    panelLife.revalidate();
                }
            }
        );


        getPlayer().addPropertyChangeListener(IPlayer.ATTACK_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelAttack.removeAll();
                    JPanel p = new JPanel(new FlowLayout()); {
                        p.add(imageAttack());
                        JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                        p.add(caption);
                    }
                    panelAttack.add(p);
                    panelAttack.revalidate();
                }
            }
        );

        getPlayer().addPropertyChangeListener(IPlayer.DEFENSE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    panelDefensive.removeAll();
                    JPanel p = new JPanel(new FlowLayout()); {
                        p.add(imageDefensive());
                        JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                        p.add(caption);
                    }
                    panelDefensive.add(p);
                    panelDefensive.revalidate();
                }
            }
        );
    }

    private JLabel imageDefensive() {
        ImageIcon icon = new ImageIcon("images/bouclier.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
    }

    private JLabel imageAttack() {
        ImageIcon icon = new ImageIcon("images/epee.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
    }

    private JLabel imageLife() {
        ImageIcon icon = new ImageIcon("images/coeur.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
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
