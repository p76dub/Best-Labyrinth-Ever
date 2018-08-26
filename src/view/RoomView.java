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
            imageEntity();
        } else if (model.getItem() != null) {
            imageCandy();
        } else if (model.getMaze().getPrincess().getRoom().equals(model)) {
            imagePrincess();
        }
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        refresh();
        this.setBackground(DEFAULT_BACKGROUND);
    }

    private void placeComponents() {
        this.add(image);
    }

    private void createController() {
        EntityPositionKeeper.getInstance().addPropertyChangeListener(EntityPositionKeeper.ROOM_PROPERTY,
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (evt.getOldValue() != null && evt.getOldValue().equals(model)) {
                                image.removeAll();
                                if (EntityPositionKeeper.getInstance().getEntities(model).size() == 0) {
                                    //Rajoute le bonbon si a pas été déja pris
                                    if (model.getItem() != null && !model.getItem().isTaken()) {
                                        imageCandy();
                                    }
                                    if (model.getMaze().getPrincess().getRoom().equals(model) && !model.getMaze().getPrincess().isSafe()) {
                                        imagePrincess();
                                    }
                                } else {
                                    imageEntity();
                                }
                            }
                            if (evt.getNewValue().equals(model)) {
                                imageEntity();
                            }
                        }
                    });
                }
            }
        );
    }

    private void imageCandy() {
        ImageIcon icon = new ImageIcon("images/bonbon.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel imageIcon = new JLabel(new ImageIcon(img));
        image.add(imageIcon);
        image.revalidate();
    }

    private void imagePrincess() {
        ImageIcon icon = new ImageIcon("images/coeur.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel imageIcon = new JLabel(new ImageIcon(img));
        image.add(imageIcon);
        image.revalidate();
    }

    private void imageEntity() {
        image.removeAll();
        Collection<IEntity> entities = EntityPositionKeeper.getInstance().getEntities(model);
        if (entities.size() > 0) {
            List<IEntity> listEntities = new ArrayList<>(entities);
            IEntity entity = listEntities.get(0);
            ImageIcon icon = new ImageIcon(entity.getMazeImagePath().getPath());
            Image img = icon.getImage();
            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel imageIcon = new JLabel(new ImageIcon(img));
            image.add(imageIcon);
        }
        image.revalidate();
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
