package util.agent;

public abstract class AbstractBehaviour implements IBehaviour {
    // ATTRIBUTS
    private final IAgent agent;

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

    // COMMANDE
    @Override
    public final void run() {
        while (!done()) {
            work();
        }
    }
}
