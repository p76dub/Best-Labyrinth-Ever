package view;

import model.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Parameter extends JPanel {

    //CONSTANTES
    public final int MIN_DIFFICULTY = 1;
    public final int MAX_DIFFICULTY = 5;
    public final int INIT_DIFFICULTY = 1;
    public final int MIN_ENEMIES = 4;
    public final int MAX_ENEMIES = 10;
    public final int INIT_ENEMIES = 4;
    public static final String PROPERTY_PARAMETER = "parameter";

    // ATTRIBUTS
    private JLabel theme;
    private JComboBox<Theme> themeBox;
    private JLabel name;
    private JTextField textName;
    private JButton validate;
    private JLabel difficulty;
    private JSlider difficultyNb;
    private JLabel enemies;
    private JSlider enemiesNb;
    private PropertyChangeSupport propertySupport;

    // CONSTRUCTEUR
    public Parameter() {
        createModel();
        createView();
        placeComponents();
        createController();
    }

    //COMMANDES
    public void addPropertyChangeListener(String property, PropertyChangeListener l) {
        if (l == null) {
            throw new AssertionError("l'écouteur est null");
        }
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.addPropertyChangeListener(property, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.removePropertyChangeListener(l);
    }

    // OUTILS
    private void createModel() {
        propertySupport = new PropertyChangeSupport(this);
    }

    private void createView() {
        theme = new JLabel("Choisir le thème : ");
        themeBox = new JComboBox<>(Theme.values());
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
        this.setLayout(new BorderLayout()); {

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
            this.add(linePane, BorderLayout.CENTER);

            JPanel p = new JPanel(); {
                p.add( validate, BorderLayout.CENTER);
            }
            this.add(p, BorderLayout.SOUTH);
        }
    }

    private void createController() {
        validate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                propertySupport.firePropertyChange(PROPERTY_PARAMETER, null, textName.getText());
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
                final String filename = "Paramètres du labyrinthe";

                final int frameWidth = 800;
                final int frameHeight = 400;

                JFrame mainFrame = new JFrame(filename);
                mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);

                mainFrame.add(new Parameter());
            }
        });
    }
}
