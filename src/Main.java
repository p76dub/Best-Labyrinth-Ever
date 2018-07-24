import model.GrowingTreeGenerator;
import model.Maze;
import model.interfaces.IMaze;

public class Main {
    public static void main(String[] args) {
        //Create a new Maze
        IMaze maze = new Maze(6, 6);
        try {
            maze.build(GrowingTreeGenerator.class);
        } catch (IMaze.MazeGeneratorCreationException e) {
            e.printStackTrace();
        }
    }
}
