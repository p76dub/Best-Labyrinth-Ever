package util;

public enum Theme {
    GAMES_OF_THRONE("Games of Throne"),
    APOCALYPSE("Apocalypse");

    // ATTRIBUTS
    private final String name;

    // CONSTRUCTEUR
    Theme(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    // REQUETES
    public String getName() {
        return this.name;
    }
}
