package view;

import model.Maze;
import model.interfaces.IMaze;

public class MazeView {

    //ATTRIBUTS
    private IMaze model;

    // CONSTRUCTEUR
    public MazeView(IMaze maze) {
        createModel(maze);
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    public IMaze getModel() {
        return model;
    }

    // COMMANDES
    public void setModel(IMaze maze) {
        model = maze;
        refresh();
    }

    // OUTILS
    private void refresh() {
    }

    private void createModel(IMaze maze) {
        setModel(maze);

    }

    private void createView() {
    }

    private void placeComponents() {
        refresh();
    }

    private void createController() {
    }
}
