import model.interfaces.IMaze;
import model.interfaces.INetwork;
import model.interfaces.IRoom;
import model.maze.MazeFactory;
import util.Direction;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        //Create a new Maze
        IMaze maze = null;
        try {
            maze = MazeFactory.growingTreeGeneration(6, 6);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        INetwork<IRoom, Direction> network = maze.getNetwork();

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
