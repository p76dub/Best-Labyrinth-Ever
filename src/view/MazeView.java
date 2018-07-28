package view;

import model.Maze;
import model.generators.GeneratorFactory;
import model.interfaces.IMaze;
import model.interfaces.IRoom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MazeView extends JPanel {

    // ATTRIBUTS
    public final int BORDER_SIZE = 1;

    private final int SIZE = 600;

    private IMaze model;
    private RoomView[][] rooms;

    // CONSTRUCTEUR
    public MazeView(IMaze model) {
        createModel(model);
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    public IMaze getModel() {
        return model;
    }

    // COMMANDES
    public void setModel(IMaze model) {
        GeneratorFactory.growingTreeGeneration(model);
        IRoom[][] allRooms = model.getRooms();
        for (int i = 0; i < model.colsNb(); ++i) {
            for (int j = 0; j < model.rowsNb(); ++j) {
                rooms[j][i].setModel(allRooms[j][i]);
            }
        }
    }

    /*
    public void placePlayer(int x, int y) {
        rooms[x][y].setImage("player.png");
    }
    */

    // OUTILS
    private void createModel(IMaze model) {
        this.model = model;
    }

    private void createView() {
        rooms = new RoomView[model.colsNb()][model.rowsNb()];
        IRoom[][] allRooms = model.getRooms();
        for (int i = 0; i < model.colsNb(); ++i) {
            for (int j = 0; j < model.rowsNb(); ++j) {
                rooms[j][i] = new RoomView(allRooms[j][i]);
            }
        }
    }

    private void placeComponents() {
        this.setLayout(new BorderLayout()); {
            JPanel p = new JPanel(new GridLayout(model.colsNb(), model.rowsNb())); {
                for (int i = 0; i < model.colsNb(); ++i) {
                    for (int j = 0; j < model.rowsNb(); ++j) {
                        p.add(rooms[j][i]);
                    }
                }
            }
            p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            this.add(p, BorderLayout.CENTER);
        }
    }

    private void createController() {
    }

    // TEST
    public static void main(String[] args) {
        class Bla {
            private static final String filename = "labyrinthe";
            JFrame mainFrame = new JFrame(filename);
            IMaze model;
            public Bla() {
                model = new Maze();
                GeneratorFactory.backTrackingGenerator(model);
                mainFrame.add(new MazeView(model), BorderLayout.CENTER);
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