package test;

import model.Maze;
import model.generators.GeneratorFactory;
import model.interfaces.IMaze;
import model.interfaces.IRoom;
import util.Direction;

public class TestMaze {

    private Maze maze;

    public TestMaze(Maze m) {
      this.maze = m;
    }

    public IMaze getMaze() {
        return maze;
    }

    public String describe() {
        String result = "Labyrinthe créé\n";
        IRoom [][] rooms = maze.getRooms();
        IRoom entry = maze.entry();
        IRoom exit = maze.exit();
        for (int i = 0; i < maze.rowsNb(); i++) {
            /*
            //ligne entrée/ sortie
            if (i == 0) {
                for (int j = 0; j < maze.colsNb(); j++) {
                    if (rooms[0][j].equals(entry)) {
                        result += "|S|";
                    } else if (rooms[0][j].equals(exit)) {
                        result += "|E|";
                    } else {
                        result += "  ";
                    }
                }
                result += '\n';
            }
            */
            //1er ligne de la pièce
            for (int j = 0; j < maze.colsNb(); j++) {
                if (rooms[j][i].canExitIn(Direction.NORTH)) {
                    result += "+ ";
                } else {
                    result += "+-";
                }
                //ligne plus à droite
                if (j == maze.colsNb() - 1) {
                    result += "+";
                }
            }
            result += '\n';

            //2ème ligne de la pièce
            for (int j = 0; j < maze.colsNb(); j++) {
                if (!rooms[j][i].canExitIn(Direction.WEST)) {
                    result += "| ";
                } else{
                    result += "  ";
                }

                //ligne plus à droite
                if (j == maze.colsNb() - 1) {
                    if (!rooms[j][i].canExitIn(Direction.EAST)) {
                        result += "|";
                    } else {
                        result += " ";
                    }
                }
            }
            result += '\n';

            //dernière ligne
            if (i == maze.colsNb() - 1) {
                for (int j = 0; j < maze.colsNb(); j++) {
                    if (rooms[j][i].canExitIn(Direction.SOUTH)) {
                        result += "+ ";
                    } else {
                        result += "+-";
                    }
                    //ligne plus à droite
                    if (j == maze.colsNb() - 1) {
                        result += "+";
                    }
                }
                result += '\n';
            }
            /*
            //ligne entrée/sortie
            if (i == maze.rowsNb() - 1) {
                for (int j = 0; j < maze.colsNb(); j++) {
                    if (rooms[i][j].equals(entry)) {
                        result += "|S|";
                    } else if (rooms[i][j].equals(exit)) {
                        result += "|E|";
                    } else {
                        result += "  ";
                    }
                }
            }
            */
        }
        result += '\n';
        /*
        result += "Portes des directions des pièces\n";
        for (int i = 0; i < maze.rowsNb(); i++) {
            for (int j = 0; j < maze.colsNb(); j++) {
                result += "Room["+j+"]["+i+"] = ";
                if (!rooms[j][i].canExitIn(Direction.NORTH)) {
                    result += "N|";
                }
                if (!rooms[j][i].canExitIn(Direction.WEST)) {
                    result += "W|";
                }
                if (!rooms[j][i].canExitIn(Direction.EAST)) {
                    result += "E|";
                }
                if (!rooms[j][i].canExitIn(Direction.SOUTH)) {
                    result += "S";
                }
                result += '\n';

            }
        }
        */
        result += "\nVérifications des jonctions\n";
        for (int i = 0; i < maze.rowsNb(); i++) {
            for (int j = 0; j < maze.colsNb(); j++) {
                if (rooms[j][i].canExitIn(Direction.NORTH)) {
                    if (i > 0 && !rooms[j][i - 1].canExitIn(Direction.SOUTH)) {
                        result += "Erreur Room["+j+"]["+i+"] vers NORD Room["+(j)+"]["+(i-1)+"]\n";
                    }
                }
                if (rooms[j][i].canExitIn(Direction.WEST)) {
                    if (j > 0 && !rooms[j - 1][i].canExitIn(Direction.EAST)) {
                        result += "Erreur Room["+j+"]["+i+"] vers OUEST Room["+(j-1)+"]["+(i)+"]\n";
                    }
                }
                if (rooms[j][i].canExitIn(Direction.EAST)) {
                    if (j < maze.colsNb() - 1 && !rooms[j+1][i].canExitIn(Direction.WEST)) {
                        result += "Erreur Room["+j+"]["+i+"] vers EST Room["+(j+1)+"]["+(i)+"]\n";
                    }
                }
                if (rooms[j][i].canExitIn(Direction.SOUTH)) {
                    if (i < maze.rowsNb() - 1 && !rooms[j][i +1].canExitIn(Direction.NORTH)) {
                        result += "Erreur Room["+j+"]["+i+"] vers SUD Room["+(j)+"]["+(i+1)+"]\n";
                    }
                }
            }
        }
        return result;
    }

    // POINT D'ENTREE
    public static void main(String[] args) {
        TestMaze game = new TestMaze(new Maze(20,20));
        GeneratorFactory.growingTreeGeneration(game.getMaze());
        System.out.println(game.describe());
    }
}
