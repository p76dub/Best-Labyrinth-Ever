package model.maze;

import model.interfaces.IMaze;

import java.net.URISyntaxException;

public class MazeFactory {
    // STATICS
    public static IMaze growingTreeGeneration(int width, int height) throws URISyntaxException {
        IMazeGenerator generator = new GrowingTreeGenerator(width, height);
        generator.generate();
        return generator.getMaze();
    }

    public static IMaze huntAndKillGeneration(int width, int height, float probability) throws URISyntaxException {
        IMazeGenerator generator = new HuntAndKillGenerator(width, height, probability);
        generator.generate();
        return generator.getMaze();
    }

    public static IMaze backTrackingGenerator(int width, int height) throws URISyntaxException {
        IMazeGenerator generator = new BackTrackingGenerator(width, height);
        generator.generate();
        return generator.getMaze();
    }

    // CONSTRUCTEUR
    private MazeFactory() {};
}
