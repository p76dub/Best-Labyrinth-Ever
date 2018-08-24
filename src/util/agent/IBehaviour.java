package util.agent;

public interface IBehaviour extends Runnable {
    // REQUETE
    /**
     * Indique si le behaviour est terminé.
     * @return true si le behaviour est terminé
     */
    boolean done();

    /**
     * Obtenir l'agent propriétaire du behaviour.
     * @return un agent
     */
    IAgent getAgent();

    // COMMANDE
    /**
     * Réalise le travail du behaviour. Tant que la méthode done ne renvoie pas true, cette méthode est rappelée dès
     * qu'elle termine.
     */
    void work();
}
