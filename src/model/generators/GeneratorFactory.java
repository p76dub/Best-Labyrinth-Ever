package model.generators;

import model.interfaces.IMaze;
import model.interfaces.IMazeGenerator;

public class GeneratorFactory {
    // CONSTANTES

    // STATICS
    public static void growingTreeGeneration(IMaze maze) {
        IMazeGenerator generator = new GrowingTreeGenerator(maze.getRooms());
        generator.generate();
    }

    public static void huntAndKillGeneration(IMaze maze, float probability) {
        IMazeGenerator generator = new HuntAndKillGenerator(maze.getRooms(), probability);
        generator.generate();
    }

    // CONSTRUCTEUR
    private GeneratorFactory() {};
}
