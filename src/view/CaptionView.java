package view;

import model.GameModel;
import model.Maze;
import model.Player;
import model.enemies.EnemyFactory;
import model.generators.GeneratorFactory;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import model.interfaces.IPrincess;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CaptionView extends JPanel {

    // ATTRIBUTS
    private GameModel model;
    private JPanel start;
    private JPanel end;
    private JPanel candyNb;
    private JPanel savePrincess;
    private JPanel panelLife;
    private JPanel panelAttack;
    private JPanel panelDefensive;

    // CONSTRUCTEUR
    public CaptionView(GameModel model) {
        createModel(model);
        createView();
        placeComponents();
        createController();
    }

    //REQUETES
    public GameModel getGame() {
        return model;
    }

    // OUTILS
    private void createModel(GameModel model) {
        this.model = model;
    }

    private void createView() {
        start = new JPanel();
        start.setBackground(MazeView.ENTRY);

        end = new JPanel();
        end.setBackground(MazeView.EXIT);

        candyNb = new JPanel();
        savePrincess = new JPanel();
        panelLife = new JPanel();
        panelAttack= new JPanel();
        panelDefensive = new JPanel();

        this.setBorder(BorderFactory.createTitledBorder("Légende"));
    }

    private void placeComponents() {
        //alignement des composants verticalement
        this.setLayout(new GridBagLayout());
        {
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;

            //ajout du départ
            JPanel s = new JPanel(new FlowLayout());
            {
                s.add(start);
                JLabel caption = new JLabel("Départ du labyrinthe");
                s.add(caption);
            }
            this.add(s, gbc);

            //ajout de l'arrivée
            s = new JPanel(new FlowLayout());
            {
                s.add(end);
                JLabel caption = new JLabel("Arrivée du labyrinthe");
                s.add(caption);
            }
            gbc.gridx += 1;
            this.add(s, gbc);

            s = new JPanel(new FlowLayout()); {
                s.add(imagePrincess());
                JLabel caption = new JLabel("princesse à sauver");
                s.add(caption);
            }
            savePrincess.add(s);

            gbc.gridx = 0;
            gbc.gridy += 1;
            this.add(savePrincess, gbc);

            candyCounter();
            gbc.gridx += 1;
            this.add(candyNb, gbc);

            //ajout de la liste d'ennemies
            int colorEnemies[] = new int[7];

            for (int i = 0; i < getGame().getEnemies().size(); i++) {
                String path = getGame().getEnemies().get(i).getMazeImagePath().getPath();
                char c = path.charAt(path.length() - (".png".length() + 1));
                int nbColor = Integer.parseInt(String.valueOf(c));
                colorEnemies[nbColor - 1] = colorEnemies[nbColor - 1] + 1;
            }

            s = new JPanel(new FlowLayout());
            {
                for (int i = 0; i < colorEnemies.length; i++) {
                    JPanel p = new JPanel(new FlowLayout());
                    {
                        p.add(imageEnemy(i + 1));
                        JLabel caption = new JLabel("x" + (colorEnemies[i]));
                        p.add(caption);
                    }
                    s.add(p);
                }
            }
            gbc.gridx = 0;
            gbc.gridy += 1;
            gbc.gridwidth = 2;
            this.add(s, gbc);

            s = new JPanel(new FlowLayout()); {
                JLabel life = new JLabel("Mes points :");
                s.add(life);

                //ajout des points de vie
                JPanel p = new JPanel(new FlowLayout()); {
                    ImageIcon icon = new ImageIcon("images/coeur.png");
                    Image img = icon.getImage();
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    JLabel imageLife = new JLabel(new ImageIcon(img));
                    p.add(imageLife);
                    JLabel caption = new JLabel("x" + getGame().getPlayer().getLifePoints());
                    p.add(caption);
                }
                panelLife.add(p);
                s.add(panelLife);

                //ajout des points d'attaque
                p = new JPanel(new FlowLayout()); {
                    ImageIcon icon = new ImageIcon("images/epee.png");
                    Image img = icon.getImage();
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    JLabel imageLife = new JLabel(new ImageIcon(img));
                    p.add(imageLife);
                    JLabel caption = new JLabel("x" + getGame().getPlayer().getAttackPoints());
                    p.add(caption);
                }
                panelAttack.add(p);
                s.add(panelAttack);

                //ajout des points de défense
                p = new JPanel(new FlowLayout()); {
                    ImageIcon icon = new ImageIcon("images/bouclier.png");
                    Image img = icon.getImage();
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    JLabel imageLife = new JLabel(new ImageIcon(img));
                    p.add(imageLife);
                    JLabel caption = new JLabel("x" + getGame().getPlayer().getDefensivePoints());
                    p.add(caption);
                }
                panelDefensive.add(p);
                s.add(panelDefensive);
            }
            gbc.gridx = 0;
            gbc.gridy += 1;
            this.add(s, gbc);
        }
    }

    private void createController() {
        this.getGame().getPlayer().addPropertyChangeListener(IPlayer.TAKE_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    candyCounter();
                }
            }
        );

        this.getGame().getMaze().getPrincess().addPropertyChangeListener(IPrincess.SAFE_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        savePrincess.removeAll();
                        JPanel s = new JPanel(new FlowLayout()); {
                            s.add(imagePrincess());
                            JLabel caption = new JLabel("princesse sauvée");
                            s.add(caption);
                        }
                        savePrincess.add(s);
                        savePrincess.revalidate();
                    }
                }
        );

        getGame().getPlayer().addPropertyChangeListener(IPlayer.LIFE_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        panelLife.removeAll();
                        JPanel p = new JPanel(new FlowLayout()); {
                            ImageIcon icon = new ImageIcon("images/coeur.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel imageLife = new JLabel(new ImageIcon(img));
                            p.add(imageLife);
                            JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                            p.add(caption);
                        }
                        panelLife.add(p);
                        panelLife.revalidate();
                    }
                }
        );


        getGame().getPlayer().addPropertyChangeListener(IPlayer.ATTACK_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        panelAttack.removeAll();
                        JPanel p = new JPanel(new FlowLayout()); {
                            ImageIcon icon = new ImageIcon("images/epee.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel imageLife = new JLabel(new ImageIcon(img));
                            p.add(imageLife);
                            JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                            p.add(caption);
                        }
                        panelAttack.add(p);
                        panelAttack.revalidate();
                    }
                }
        );

        getGame().getPlayer().addPropertyChangeListener(IPlayer.DEFENSE_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        panelDefensive.removeAll();
                        JPanel p = new JPanel(new FlowLayout()); {
                            ImageIcon icon = new ImageIcon("images/bouclier.png");
                            Image img = icon.getImage();
                            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel imageLife = new JLabel(new ImageIcon(img));
                            p.add(imageLife);
                            JLabel caption = new JLabel("x" + (int) evt.getNewValue());
                            p.add(caption);
                        }
                        panelDefensive.add(p);
                        panelDefensive.revalidate();
                    }
                }
        );
    }

    //ajout de la liste des items
    private void candyCounter() {
        int count = 0;
        candyNb.removeAll();

        for (int i = 0; i < getGame().getItems().size(); i++) {
            count += getGame().getItems().get(i).isTaken() ? 0 : 1;
        }

        JPanel s = new JPanel(new FlowLayout()); {
            s.add(imageCandy());
            JLabel caption = new JLabel("x"+count);
            s.add(caption);
        }
        candyNb.add(s);

        candyNb.revalidate();
    }
    private JLabel imageCandy() {
        ImageIcon icon = new ImageIcon("images/bonbon.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
    }

    private JLabel imageEnemy(int i) {
        ImageIcon icon = new ImageIcon("images/enemy" + i + ".png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
    }

    private JLabel imagePrincess() {
        ImageIcon icon = new ImageIcon("images/coeur.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(img));
    }

    // TEST
    public static void main(String[] args) {
        class Bla {
            private static final String filename = "Légende";
            JFrame mainFrame = new JFrame(filename);

            public Bla() throws URISyntaxException {
                Map<Direction, URI> map = new HashMap<>();
                map.put(Direction.EAST, getClass().getResource("../player.png").toURI());
                map.put(Direction.WEST, getClass().getResource("../player_left.png").toURI());
                map.put(Direction.NORTH, getClass().getResource("../player_top.png").toURI());
                map.put(Direction.SOUTH, getClass().getResource("../player_bottom.png").toURI());

                IPlayer player = new Player("test", 1, 1, 1, map);

                IMaze maze = new Maze();
                GeneratorFactory.backTrackingGenerator(maze);

                Collection enemies = new ArrayList();
                enemies.add(EnemyFactory.createZombie());
                enemies.add(EnemyFactory.createZombie2());
                enemies.add(EnemyFactory.createZombie3());

                GameModel model = new GameModel(maze, player, enemies, new ArrayList<>());

                mainFrame.add(new CaptionView(model), BorderLayout.CENTER);
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
                try {
                    new Bla().display();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
