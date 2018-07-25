import model.GrowingTreeGenerator;
import model.Maze;
import model.interfaces.IMaze;
import model.interfaces.IRoom;
import util.Direction;

public class Game {

    private Maze maze;

    public Game(Maze m) {
      this.maze = m;
    }

    public IMaze getMaze() {
        return maze;
    }

    public String describe() {
        String result = "Labyrinthe crée\n";
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
                if (rooms[i][j].canExitIn(Direction.NORTH)) {
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
                if (!rooms[i][j].canExitIn(Direction.EAST)) {
                    result += "| ";
                } else{
                    result += "  ";
                }

                //ligne plus à droite
                if (j == maze.colsNb() - 1) {
                    if (!rooms[i][j].canExitIn(Direction.WEST)) {
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
                    if (rooms[i][j].canExitIn(Direction.SOUTH)) {
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
        result += "Portes des directions des pièces\n";
        for (int i = 0; i < maze.rowsNb(); i++) {
            for (int j = 0; j < maze.colsNb(); j++) {
                result += "Room["+i+"]["+j+"] = ";
                if (!rooms[i][j].canExitIn(Direction.NORTH)) {
                    result += "N|";
                }
                if (!rooms[i][j].canExitIn(Direction.EAST)) {
                    result += "E|";
                }
                if (!rooms[i][j].canExitIn(Direction.WEST)) {
                    result += "W|";
                }
                if (!rooms[i][j].canExitIn(Direction.SOUTH)) {
                    result += "S";
                }
                result += '\n';

            }
        }
        result += "\nVérifications des jonctions\n";
        for (int i = 0; i < maze.rowsNb(); i++) {
            for (int j = 0; j < maze.colsNb(); j++) {
                if (rooms[i][j].canExitIn(Direction.NORTH)) {
                    if (i > 0 && !rooms[i - 1][j].canExitIn(Direction.SOUTH)) {
                        result += "Erreur Room["+i+"]["+j+"] vers NORD Room["+(i-1)+"]["+j+"]\n";
                    }
                }
                if (rooms[i][j].canExitIn(Direction.EAST)) {
                    if (j > 0 && !rooms[i][j-1].canExitIn(Direction.WEST)) {
                        result += "Erreur Room["+i+"]["+j+"] vers EST Room["+i+"]["+(j-1)+"]\n";
                    }
                }
                if (rooms[i][j].canExitIn(Direction.WEST)) {
                    if (j < maze.colsNb() - 1 && !rooms[i][j+1].canExitIn(Direction.EAST)) {
                        result += "Erreur Room["+i+"]["+j+"] vers OUEST Room["+i+"]["+(j+1)+"]\n";
                    }
                }
                if (rooms[i][j].canExitIn(Direction.SOUTH)) {
                    if (i < maze.rowsNb() - 1 && !rooms[i + 1][j].canExitIn(Direction.NORTH)) {
                        result += "Erreur Room["+i+"]["+j+"] vers SUD Room["+(i+1)+"]["+j+"]\n";
                    }
                }
            }
        }
        return result;
    }

    // POINT D'ENTREE
    public static void main(String[] args) {
        Game game = new Game(new Maze(5,5));
        try {
            game.getMaze().build(GrowingTreeGenerator.class);
            System.out.println(game.describe());
        } catch (IMaze.MazeGeneratorCreationException e) {
            e.printStackTrace();
        }
    }
}
