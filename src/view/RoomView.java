package view;

import model.EntityPositionKeeper;
import model.Maze;
import model.Room;
import model.interfaces.IEntity;
import model.interfaces.IPlayer;
import model.interfaces.IRoom;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class RoomView extends JPanel {
    // STATICS
    public static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private static final int SIZE = 30;

    // ATTRIBUTS
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
        image.setOpaque(false);
        if (model.getEntities().size() != 0) {
            // FIXME: temporaire, le joueur n'a pour le moment pas d'ennemis
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
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        refresh();
        this.setBackground(DEFAULT_BACKGROUND);
    }

    private void placeComponents() {
        this.add(image);
    }

    private void createController() {
        RoomView view = this;
        EntityPositionKeeper.getInstance().addPropertyChangeListener(EntityPositionKeeper.ROOM_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getOldValue().equals(model)) {
                        if (EntityPositionKeeper.getInstance().getEntities(model).size() == 0) {
                            image.removeAll();
                        }
                    }
                    if (evt.getNewValue().equals(model)) {
                        view.add(image);
                        image.setBackground(DEFAULT_BACKGROUND);
                        Collection<IEntity> entities = EntityPositionKeeper.getInstance().getEntities(model);
                        List<IEntity> listEntities = new ArrayList<>(entities);
                        IEntity entity = listEntities.get(0);
                        ImageIcon icon = new ImageIcon(entity.getMazeImagePath().getPath());
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
                try {
                    mainFrame.add(new RoomView(new Room(new Maze())), BorderLayout.CENTER);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
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
