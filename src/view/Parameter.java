package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Parameter {

    //CONSTANTES
    public final String[] THEME = { "Aucun", "Harry Potter", "Game Of Throne",
                                    "Pokemon", "Star Wars", "Seigneur des Anneaux"};
    public final int MIN_DIFFICULTY = 1;
    public final int MAX_DIFFICULTY = 5;
    public final int INIT_DIFFICULTY = 1;
    public final int MIN_ENEMIES = 4;
    public final int MAX_ENEMIES = 10;
    public final int INIT_ENEMIES = 4;

    // ATTRIBUTS
    private JFrame mainFrame;
    private JLabel theme;
    private JComboBox themeBox;
    private JLabel name;
    private JTextField textName;
    private JButton validate;
    private JLabel difficulty;
    private JSlider difficultyNb;
    private JLabel enemies;
    private JSlider enemiesNb;

    // CONSTRUCTEUR
    public Parameter() {
        createModel();
        createView();
        placeComponents();
        createController();
    }

    // REQUETES

    // COMMANDES
    public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // OUTILS
    private void createModel() {
    }

    private void createView() {
        final int frameWidth = 400;
        final int frameHeight = 400;

        mainFrame = new JFrame("Paramètres du labyrinthe");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theme = new JLabel("Choisir le thème : ");
        themeBox = new JComboBox(THEME);
        themeBox.setPreferredSize(new Dimension(200, 30));
        themeBox.setMaximumRowCount(5);

        name = new JLabel("Nom de votre héro : ");
        textName = new JTextField(20);

        difficulty = new JLabel("Niveau de difficulté :");
        difficultyNb = new JSlider(JSlider.HORIZONTAL, MIN_DIFFICULTY,
                MAX_DIFFICULTY, INIT_DIFFICULTY);
        difficultyNb.setMajorTickSpacing(1);
        difficultyNb.setMinorTickSpacing(1);
        difficultyNb.setPaintTicks(true);
        difficultyNb.setPaintLabels(true);

        enemies = new JLabel("Niveau des ennemies :");
        enemiesNb = new JSlider(JSlider.HORIZONTAL, MIN_ENEMIES,
                MAX_ENEMIES, INIT_ENEMIES);
        enemiesNb.setMajorTickSpacing(1);
        enemiesNb.setMinorTickSpacing(1);
        enemiesNb.setPaintTicks(true);
        enemiesNb.setPaintLabels(true);

        validate = new JButton("Valider");
        validate.setEnabled(false);
    }

    private void placeComponents() {
        mainFrame.setLayout(new BorderLayout()); {

            JPanel linePane = new JPanel(new GridLayout(4,0)); {

                JPanel p = new JPanel(); {
                    p.add(name);
                    p.add(textName);
                }
                linePane.add(p);

                p = new JPanel(); {
                    p.add(theme);
                    p.add(themeBox);
                }
                linePane.add(p);

                p = new JPanel(); {
                    p.add(enemies);
                    p.add(enemiesNb);
                }
                linePane.add(p);

                p = new JPanel(); {
                    p.add(difficulty);
                    p.add(difficultyNb);
                }
                linePane.add(p);
            }

            linePane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            mainFrame.add(linePane, BorderLayout.CENTER);

            JPanel p = new JPanel(); {
                p.add( validate, BorderLayout.CENTER);
            }
            mainFrame.add(p, BorderLayout.SOUTH);
        }
    }

    private void createController() {
        validate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new Game(textName.getText()).display();
                mainFrame.dispose();
            }
        });

        textName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validate.setEnabled(textName.getText().length() > 0);
            }
        });
    }

    // LANCEUR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Parameter().display();
            }
        });
    }
}
