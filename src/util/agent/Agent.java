package util.agent;

import java.util.*;

public class Agent implements IAgent {
    // CLASSES
    private class SharedState {
        private boolean paused;
        private boolean stopped;
        private boolean started;

        synchronized boolean isPaused() {
            return started && !stopped && paused;
        }

        synchronized boolean isStopped() {
            return started && stopped;
        }

        synchronized boolean isStarted() {
            return started;
        }

        synchronized boolean isRunning() {
            return started && !stopped && !paused;
        }

        synchronized void setPaused() {
            paused = true;
        }

        synchronized void setStopped() {
            stopped = true;
        }

        synchronized void setStarted() {
            started = true;
        }

        synchronized void setRunning() {
            paused = false;
        }
    }

    private class InternalThread extends Thread {
        private final SharedState sharedState;

        public InternalThread() {
            this.sharedState = new SharedState();
        }

        public SharedState getInternalState() {
            return sharedState;
        }

        @Override
        public void run() {
            Agent.this.initialization();

            while (!sharedState.isStopped()) {
                synchronized (sharedState) {
                    while (sharedState.isPaused()) {
                        try {
                            sharedState.wait();
                        } catch (InterruptedException ignored) {}
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
                if (!sharedState.isStopped()) {
                    Random r = new Random();
                    if (Agent.this.behaviours.size() > 0) {
                        Agent.this.behaviours.get(r.nextInt(Agent.this.behaviours.size())).run();
                    }
                }

            }

            Agent.this.cleanup();
        }
    }

    // ATTRIBUTS
    private final List<IBehaviour> behaviours;
    private final String name;
    private InternalThread internalThread;

    // CONSTRUCTEUR
    public Agent(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        behaviours = new LinkedList<>();
        internalThread = new InternalThread();
    }

    // REQUETES
    @Override
    public Collection<IBehaviour> getBehaviours() {
        return new ArrayList<>(this.behaviours);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isRunning() {
        return internalThread.getInternalState().isRunning();
    }

    @Override
    public boolean isStarted() {
        return internalThread.getInternalState().isStarted();
    }

    @Override
    public boolean isPaused() {
        return internalThread.getInternalState().isPaused();
    }

    @Override
    public boolean isStopped() {
        return internalThread.getInternalState().isStopped();
    }

    // COMMANDES
    @Override
    public void initialization() {
        // Nothing
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

    @Override
    public void start() {
        if (isRunning()) {
            throw new AssertionError();
        }
        if (internalThread == null) {
            internalThread = new InternalThread();
        }
        internalThread.getInternalState().setStarted();
        internalThread.start();
    }

    @Override
    public void stop() {
        if (isStopped()) {
            throw new AssertionError();
        }
        SharedState state = internalThread.getInternalState();
        if (isPaused()) {
            resume();
        }
        state.setStopped();
        while (internalThread.isAlive()) {
            try {
                internalThread.join();
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void pause() {
        if (isPaused()) {
            throw new AssertionError();
        }
        internalThread.getInternalState().setPaused();
    }

    @Override
    public void resume() {
        if (!isPaused()) {
            throw new AssertionError();
        }
        SharedState state = internalThread.getInternalState();
        state.setRunning();
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (state) {
            state.notifyAll();
        }
    }
}
