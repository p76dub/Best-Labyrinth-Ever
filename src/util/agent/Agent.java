package util.agent;

import java.util.*;

public class Agent extends Thread implements IAgent {
    // ATTRIBUTS
    private final List<IBehaviour> behaviours;
    private boolean running;

    // CONSTRUCTEUR
    public Agent(String name) {
        super(name);
        behaviours = new LinkedList<>();
        running = false;
    }

    // REQUETES
    @Override
    public Collection<IBehaviour> getBehaviours() {
        return new ArrayList<>(this.behaviours);
    }

    @Override
    public synchronized boolean isRunning() {
        return running;
    }

    // COMMANDES
    @Override
    public void initialization() {
        // Nothing
    }

    @Override
    public final void run() {
        setRunning(true);
        this.initialization();

        while (isRunning()) {
            Random r = new Random();
            this.behaviours.get(r.nextInt(this.behaviours.size())).run();
        }

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

    // OUTILS
    synchronized protected final void setRunning(boolean running) {
        this.running = running;
    }
}
