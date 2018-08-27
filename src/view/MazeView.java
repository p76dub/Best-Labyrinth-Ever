package view;

import model.interfaces.IMaze;
import model.interfaces.IRoom;
import model.maze.MazeFactory;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.net.URISyntaxException;

public class MazeView extends JPanel {

    // CONSTANTES
    public static final Color ENTRY = Color.RED;
    public static final Color EXIT = Color.GREEN;

    // ATTRIBUTS
    private IMaze model;
    private RoomView[][] rooms;

    // CONSTRUCTEUR
    public MazeView(IMaze model) {
        createModel(model);
        createView();
        placeComponents();
        createController();
        exitAndEntrySee();
    }

    // REQUETES
    public IMaze getModel() {
        return model;
    }
    public RoomView[][] getRoomView() { return rooms; }

    // COMMANDES
    public void setModel(IMaze model) {
        IRoom[][] allRooms = model.getRooms();
        for (int i = 0; i < model.colsNb(); ++i) {
            for (int j = 0; j < model.rowsNb(); ++j) {
                rooms[j][i].setModel(allRooms[j][i]);
            }
        }
    }

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

    private void exitAndEntrySee() {
        for (int i = 0; i < model.colsNb(); ++i) {
            for (int j = 0; j < model.rowsNb(); ++j) {
                if (model.getRooms()[i][j].equals(model.exit())) {
                    getRoomView()[i][j].setBackground(EXIT);
                }
                if (model.getRooms()[i][j].equals(model.entry())) {
                    getRoomView()[i][j].setBackground(ENTRY);
                }
            }
        }
    }
    // TEST
    public static void main(String[] args) {
        class Bla {
            private static final String filename = "labyrinthe";
            JFrame mainFrame = new JFrame(filename);
            IMaze model;
            public Bla() {
                try {
                    model = MazeFactory.backTrackingGenerator(IMaze.DEFAULT_WIDTH, IMaze.DEFAULT_HEIGHT);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

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