package view;

import model.Maze;

public class MazeView {

    //ATTRIBUTS
    private Maze model;

    // CONSTRUCTEUR
    public MazeView(Maze maze) {
        createModel(maze);
        createView();
        placeComponents();
        createController();
    }

    // REQUETES
    public Maze getModel() {
        return model;
    }

    // COMMANDES
    public void setModel(Maze maze) {
        model = maze;
        refresh();
    }

    // OUTILS
    private void refresh() {
    }

    private void createModel() {

    }

    private void createView() {
    }

    private void placeComponents() {
        refresh();
    }

    private void createController() {
    }
}
