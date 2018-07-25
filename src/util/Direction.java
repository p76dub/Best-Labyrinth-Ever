package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumère toutes les directions connues de Pyland :
 *     {NORTH, EAST, SOUTH, WEST}
 * Une direction peut aussi être obtenue par son nom.
 * @inv <pre>
 *     NORTH.name().equals("top")
 *     EAST.name().equals("left")
 *     SOUTH.name().equals("bottom")
 *     WEST.name().equals("right")
 *     NORTH.opposite() == SOUTH
 *     EAST.opposite() == WEST
 *     SOUTH.opposite() == NORTH
 *     WEST.opposite() == EAST
 *     </pre>
 */
public final class Direction {

    // Constantes
    public static final Direction NORTH = new Direction("top");
    public static final Direction EAST  = new Direction("right");
    public static final Direction SOUTH = new Direction("bottom");
    public static final Direction WEST  = new Direction("left");

    private static final Map<String, Direction> DIRECTIONS;

    static {
        DIRECTIONS = new HashMap<>();
        DIRECTIONS.put("top", NORTH);
        DIRECTIONS.put("right", EAST);
        DIRECTIONS.put("bottom", SOUTH);
        DIRECTIONS.put("left", WEST);
    }

    private static final Map<Direction, Direction> OPPOSITES;
    static {
        OPPOSITES = new HashMap<>();
        OPPOSITES.put(NORTH, SOUTH);
        OPPOSITES.put(EAST, WEST);
        OPPOSITES.put(SOUTH, NORTH);
        OPPOSITES.put(WEST, EAST);
    }

    // Attribut
    private final String name; // Le nom interne de la direction

    // Constructeur
    /**
     * Une nouvelle direction.
     * @param name
     * @pre name != null
     * @post name().equals(name)
     */
    private Direction(String name) {
        assert name != null;
        this.name = name;
    }

    // Requêtes
    public String name() {
        return name;
    }

    public Direction opposite() {
        return OPPOSITES.get(this);
    }

    // Outils
    /**
     * Convertit si possible la chaîne name en Direction.
     * @param name
     * @pre name != null
     * @post <pre>
     *     name.toLowerCase().beginsWith("top") ==> result == NORTH
     *     name.toLowerCase().beginsWith("left") ==> result == EAST
     *     name.toLowerCase().beginsWith("bottom") ==> result == SOUTH
     *     name.toLowerCase().beginsWith("right") ==> result == WEST
     *     dans tous les autres cas           ==> result == null
     *     </pre>
     */
    public static Direction valueOf(String name) {
        if (name == null) {
            throw new AssertionError();
        }
        String n = String.valueOf(name).toLowerCase();
        return DIRECTIONS.get(n);
    }

    /**
     * Un tableau sur toutes les directions possibles.
     * @post result est un tableau de la forme [NORTH, EAST, SOUTH, WEST]
     */
    public static Direction[] allDirections() {
        return new Direction[] { NORTH, EAST, SOUTH, WEST };
    }
}
