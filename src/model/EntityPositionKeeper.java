package model;

import model.interfaces.IEntity;
import model.interfaces.IRoom;

import java.util.*;

public class EntityPositionKeeper {
    // STATICS
    private static EntityPositionKeeper instance;

    public static EntityPositionKeeper getInstance() {
        if (instance == null) {
            instance = new EntityPositionKeeper();
        }
        return instance;
    }

    // ATTRIBUTS
    private final Map<IEntity, IRoom> positions;
    private final Map<IRoom, Set<IEntity>> reversed;

    private EntityPositionKeeper() {
        positions = new HashMap<>();
        reversed = new HashMap<>();
    }

    public List<List<IEntity>> getOverlappingEntities() {
        List<List<IEntity>> result = new ArrayList<>();
        for (IRoom r : reversed.keySet()) {
            if (reversed.get(r).size() >= 2) {
                result.add(new ArrayList<>(reversed.get(r)));
            }
        }
        return result;
    }

    public IRoom getPosition(IEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        return positions.get(entity);
    }

    public void move(IEntity entity, IRoom room) {
        if (entity == null || room == null) {
            throw new NullPointerException();
        }
        if (!positions.containsKey(entity)) {
            throw new IllegalArgumentException();
        }
        IRoom old = positions.get(entity);
        reversed.get(old).remove(entity);

        positions.put(entity, room);
        if (!reversed.containsKey(room)) {
            reversed.put(room, new HashSet<>());
        }
        reversed.get(room).add(entity);
    }

    public void registerEntity(IEntity entity, IRoom position) {
        if (entity == null || position == null) {
            throw new NullPointerException();
        }
        if (positions.containsKey(entity)) {
            throw new IllegalArgumentException();
        }
        positions.put(entity, position);
        if (!reversed.containsKey(position)) {
            reversed.put(position, new HashSet<>());
        }
        reversed.get(position).add(entity);
    }

    public void deleteEntity(IEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        IRoom room = positions.remove(entity);
        reversed.get(room).remove(entity);
    }
}
