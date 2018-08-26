package util.agent;

import java.util.Collection;

/**
 * @inv <pre>
 *     getBehaviours() != null
 *     getName() != null
 * </pre>
 */
public interface IAgent {
    // REQUETES
    /**
     * Get all registered behaviours as a Collection. Might be an empty collection.
     * @return a collection of behaviours
     */
    Collection<IBehaviour> getBehaviours();

    /**
     * Obtenir le nom de l'agent.
     */
    String getName();

    /**
     * Indique si l'agent est en cours d'exécution ou non.
     */
    boolean isRunning();

    /**
     * Indique si l'agent a été lancé.
     */
    boolean isStarted();

    boolean isPaused();

    boolean isStopped();

    // COMMANDS
    /**
     * Called as the first line of the run method. It should perform some initialization before running the agent body.
     */
    void initialization();

    /**
     * Perform some cleaning actions just before the agent stops.
     */
    void cleanup();

    /**
     * Add a behaviour to the agent.
     * @param behaviour the added behaviour
     * @pre behaviour != null
     *
     */
    void addBehaviour(IBehaviour behaviour);

    /**
     * Remove a behaviour from the registered behaviours. If behaviour is not already registered, ignored (weak removal)
     * @param behaviour the behaviour you want to delete.
     */
    void removeBehaviour(IBehaviour behaviour);

    /**
     * Démarrer l'agent.
     */
    void start();

    /**
     * Arrêter définitivement l'agent.
     */
    void stop();

    /**
     * Mettre en pause l'agent.
     */
    void pause();

    /**
     * Reprendre l'exécution de l'agent.
     */
    void resume();
}
