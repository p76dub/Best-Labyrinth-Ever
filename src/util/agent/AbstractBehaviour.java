package util.agent;

public abstract class AbstractBehaviour implements IBehaviour {
    // ATTRIBUTS
    private final IAgent agent;
    private boolean done;

    // CONSTRUCTEUR
    public AbstractBehaviour(IAgent agent) {
        if (agent == null) {
            throw new NullPointerException();
        }
        this.agent = agent;
    }

    // REQUETE
    @Override
    public IAgent getAgent() {
        return agent;
    }

    @Override
    public boolean done() {
        return done;
    }

    // COMMANDE
    @Override
    public final void run() {
        while (!done()) {
            work();
        }
    }

    // PROTECTED
    protected void setDone(boolean value) {
        this.done = value;
    }
}
