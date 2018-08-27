package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class Menu {

    //ATTRIBUTS
    private JFrame mainFrame;

    private JMenuBar menuBar;
    private JMenu play;
    private JMenu rules;
    private JMenu parameters;

    private JMenuItem newGame;
    private JMenuItem quitGame;
    private JMenuItem quit;
    private JMenuItem rule;

    private Parameter params;
    private Game game;
    private JPanel first;


    public Menu(){
        createModel();
        createView();
        placeComponents();
        createController();
    }

    private void createModel() {
        mainFrame = new JFrame();

        menuBar = new JMenuBar();
        play = new JMenu("Partie");
        rules = new JMenu("Règles");
        parameters = new JMenu("Paramètres");

        newGame = new JMenuItem("Nouvelle partie");
        quitGame = new JMenuItem("Quitter la partie");
        quit = new JMenuItem("Fermer");
        rule = new JMenuItem("Règles du jeu");

        params = new Parameter();
        game = null;
        first = new JPanel(new BorderLayout());
    }

    private void createView() {
        mainFrame.setSize(800,400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Labyrinthe");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void placeComponents() {
        //On initialise nos menus
        this.play.add(newGame);

        //Ajout d'un séparateur
        this.play.addSeparator();

        this.play.add(quitGame);

        this.rules.add(rule);
        this.parameters.add(quit);

        //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
        //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
        this.menuBar.add(play);
        this.menuBar.add(rules);
        this.menuBar.add(parameters);

        mainFrame.setJMenuBar(menuBar);

        first.add(params, BorderLayout.CENTER);
        mainFrame.add(first);
    }

    private void createController() {
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        params.addPropertyChangeListener(Parameter.PROPERTY_PARAMETER,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                String namePlayer = (String) evt.getNewValue();
                                mainFrame.requestFocus();
                                game = new Game(namePlayer, mainFrame);
                                first.removeAll();
                                first.add(game);
                                first.revalidate();
                            }
                        });
                    }
                }
        );
    }

    // LANCEUR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu();
            }
        });
    }
}