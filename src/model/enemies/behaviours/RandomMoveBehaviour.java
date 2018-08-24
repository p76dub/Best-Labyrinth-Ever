package model.enemies.behaviours;

import model.EntityPositionKeeper;
import model.interfaces.IEnemy;
import model.interfaces.IEntity;
import model.interfaces.IRoom;
import util.Direction;
import util.agent.AbstractBehaviour;
import util.agent.IAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMoveBehaviour extends AbstractBehaviour {
    // ATTRIBUTS
    private final int sleepTime;

    // CONSTRUCTEUR
    public RandomMoveBehaviour(IEnemy agent, int sleepTime) {
        super(agent);
        if (sleepTime < 0) {
            throw new IllegalArgumentException();
        }
        this.sleepTime = sleepTime;
    }
    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void work() {
        // On commence par dormir un peu
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // Réveil innatendu, on ignore
        }

        // On fait le travail
        IAgent agent = getAgent();
        IRoom room =EntityPositionKeeper.getInstance().getPosition((IEntity) agent);
        List<Direction> possibleDirection = new ArrayList<>();

        // Choisir une pièce accessible
        for (Direction d : Direction.allDirections()) {
            if (room.canExitIn(d)) {
                possibleDirection.add(d);
            }
        }

        Random generator = new Random();
        Direction selectedDirection = possibleDirection.get(generator.nextInt(possibleDirection.size()));

        // Déplacer l'entité
        ((IEntity) agent).move(selectedDirection);

        // Terminer le behaviour
        setDone(true);
    }
}
