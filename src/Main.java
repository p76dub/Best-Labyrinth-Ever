import model.Maze;
import model.RoomNetwork;
import model.generators.GeneratorFactory;
import model.interfaces.IMaze;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import util.Direction;

public class Main {
    public static void main(String[] args) {
        //Create a new Maze
        IMaze maze = new Maze(6, 6);
        GeneratorFactory.growingTreeGeneration(maze);

        INetwork<IRoom, Direction> network = RoomNetwork.getInstance();

        for (int i = 0; i < maze.rowsNb(); ++i) {
            for (int j = 0; j < maze.colsNb(); ++j) {
                IRoom src = maze.getRooms()[i][j];
                for (Direction d: Direction.allDirections()) {
                    IRoom dst = network.get(src, d);
                    if (dst != null) {
                        System.out.println(src + " - " + d.name() + " -> " + dst + " : " + network.get(dst, d.opposite()).equals(src));
                        System.out.println("CanExitIn: " + src.canExitIn(d) + " - " + dst.canExitIn(d.opposite()));
                    }
                }
            }
        }
    }
}
