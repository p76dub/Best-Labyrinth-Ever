package util.agent;

import java.util.*;

public class Agent extends Thread implements IAgent {
    // ATTRIBUTS
    private final List<IBehaviour> behaviours;

    // CONSTRUCTEUR
    public Agent(String name) {
        super(name);
        behaviours = new LinkedList<>();
    }

    // REQUETES
    @Override
    public Collection<IBehaviour> getBehaviours() {
        return new ArrayList<>(this.behaviours);
    }

    // COMMANDES
    @Override
    public void initialization() {
        // Nothing
    }

    @Override
    public final void run() {
        this.initialization();

        Random r = new Random();
        this.behaviours.get(r.nextInt(this.behaviours.size())).run();

        this.cleanup();
    }

    @Override
    public void cleanup() {
        // Nothing
    }

    @Override
    public void addBehaviour(IBehaviour behaviour) {
        if (behaviour == null) {
            throw new NullPointerException();
        }
        this.behaviours.add(behaviour);
    }

    @Override
    public void removeBehaviour(IBehaviour behaviour) {
        if (behaviour == null) {
            throw new NullPointerException();
        }
        this.behaviours.remove(behaviour);
    }
}
