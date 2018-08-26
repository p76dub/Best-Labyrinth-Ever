package view;

import model.GameModel;
import model.Maze;
import model.Player;
import model.enemies.EnemyFactory;
import model.generators.GeneratorFactory;
import model.interfaces.IMaze;
import model.interfaces.IPlayer;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
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
        start.setBackground(Color.RED);

        end = new JPanel();
        end.setBackground(Color.GREEN);
    }

    private void placeComponents() {
        //alignement des composants verticalement
        JPanel listPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //On positionne la case de départ du composant
        gbc.gridx = 0;
        gbc.gridy = 0;

        //La taille en hauteur et en largeur
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        //ajout du départ
        JPanel s = new JPanel(new FlowLayout()); {
            s.add(start);
            JLabel caption = new JLabel("Départ du labyrinthe");
            s.add(caption);
        }
        listPane.add(s, gbc);

        //ajout de l'arrivée
        s = new JPanel(new FlowLayout()); {
            s.add(end);
            JLabel caption = new JLabel("Arrivée du labyrinthe");
            s.add(caption);
        }
        gbc.gridx += 1;
        listPane.add(s, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;
        if (!getGame().getPrincess().isSafe()) {
            s = new JPanel(new FlowLayout()); {
                ImageIcon icon = new ImageIcon("images/coeur.png");
                Image img = icon.getImage();
                img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                JLabel imageLife = new JLabel(new ImageIcon(img));
                s.add(imageLife);
                JLabel caption = new JLabel("princesse à sauver");
                s.add(caption);
            }
            listPane.add(s, gbc);
        }

        //ajout de la liste des items
        for (int i = 0; i < getGame().getItems().size(); i++) {
            s = new JPanel(new FlowLayout()); {
                ImageIcon icon = new ImageIcon("images/bonbon.png");
                Image img = icon.getImage();
                img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                JLabel imageLife = new JLabel(new ImageIcon(img));
                s.add(imageLife);
                JLabel caption = new JLabel("x"+(i+1));
                s.add(caption);
            }
        }
        if (getGame().getItems().size() ==0) {
            s = new JPanel(new FlowLayout()); {
                ImageIcon icon = new ImageIcon("images/bonbon.png");
                Image img = icon.getImage();
                img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                JLabel imageLife = new JLabel(new ImageIcon(img));
                s.add(imageLife);
                JLabel caption = new JLabel("x0");
                s.add(caption);
            }
        }
        gbc.gridx += 1;
        listPane.add(s, gbc);

        //ajout de la liste d'ennemies
        int colorEnemies[] = new int[7];

        for (int i = 0; i < getGame().getEnemies().size(); i++) {
            String path = getGame().getEnemies().get(i).getMazeImagePath().getPath();
            char c = path.charAt(path.length() - (".png".length() + 1));
            int nbColor = Integer.parseInt(String.valueOf(c));
            colorEnemies[nbColor - 1] = colorEnemies[nbColor - 1] + 1;
        }

        s = new JPanel(new FlowLayout()); {
            for (int i = 0; i < colorEnemies.length; i++) {
                JPanel p = new JPanel(new FlowLayout());
                {
                    ImageIcon icon = new ImageIcon("images/enemy" + (i+1) + ".png");
                    Image img = icon.getImage();
                    img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    JLabel imageLife = new JLabel(new ImageIcon(img));
                    p.add(imageLife);
                    JLabel caption = new JLabel("x" + (colorEnemies[i]));
                    p.add(caption);
                }
                s.add(p);
            }
        }
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 2;
        listPane.add(s, gbc);

        this.add(listPane);
    }

    private void createController() {

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
