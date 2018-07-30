package view;

import model.Maze;
import model.Room;
import model.interfaces.IRoom;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RoomView extends JPanel {

    // ATTRIBUTS
    public static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private final int SIZE = 30;

    private IRoom model;
    private JPanel image;

    // CONSTRUCTEUR
    public RoomView(IRoom room) {
        createModel(room);
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    public IRoom getModel() {
        return model;
    }

    // COMMANDES
    public void setModel(IRoom room) {
        model = room;
        refresh();
    }

    // OUTILS
    private void refresh() {
        int top = model.canExitIn(Direction.NORTH) ? 0 : 1;
        int left = model.canExitIn(Direction.WEST) ? 0 : 1;
        int right = model.canExitIn(Direction.EAST) ? 0 : 1;
        int bottom = model.canExitIn(Direction.SOUTH) ? 0 : 1;
        this.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }

    private void createModel(IRoom room) {
        model = room;
    }

    private void createView() {
        image = new JPanel();
        if (model.getPlayer() != null) {
            ImageIcon icon = new ImageIcon("images/player.png");
            Image img = icon.getImage();
            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel imageIcon = new JLabel(new ImageIcon(img));
            image.add(imageIcon);
        }
        if (model.getItem() != null) {
            ImageIcon icon = new ImageIcon("images/bonbon.png");
            Image img = icon.getImage();
            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel imageIcon = new JLabel(new ImageIcon(img));
            image.add(imageIcon);
        }
        image.setBackground(DEFAULT_BACKGROUND);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        refresh();
        this.setBackground(DEFAULT_BACKGROUND);
    }

    private void placeComponents() {
        this.add(image);
    }

    private void createController() {
        model.addPropertyChangeListener("PLAYER",
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    image.removeAll();
                    if (evt.getNewValue() != null) {
                        String direction = "";
                        if (model.getDirection() == Direction.NORTH) {
                            direction = "_top";
                        }
                        if (model.getDirection() == Direction.WEST) {
                            direction = "_left";
                        }
                        if (model.getDirection() == Direction.SOUTH) {
                            direction = "_bottom";
                        }
                        ImageIcon icon = new ImageIcon("images/player"+direction+".png");
                        Image img = icon.getImage();
                        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                        JLabel imageIcon = new JLabel(new ImageIcon(img));
                        image.add(imageIcon);
                        image.revalidate();
                    }
                }
            }
        );
    }
    // TEST
    public static void main(String[] args) {
        class Bla {
            JFrame mainFrame = new JFrame();
            public Bla() {
                mainFrame.add(new RoomView(new Room(new Maze())), BorderLayout.CENTER);
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
