package model;

import model.interfaces.IEntity;
import model.interfaces.IRoom;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class EntityPositionKeeper {
    // STATICS
    private static EntityPositionKeeper instance;
    public static final String ROOM_PROPERTY = "room";
    public static final String REMOVE_ENTITY_PROPERTY = "remove entity";

    public static EntityPositionKeeper getInstance() {
        if (instance == null) {
            instance = new EntityPositionKeeper();
        }
        return instance;
    }

    // ATTRIBUTS
    private final Map<IEntity, IRoom> positions;
    private final Map<IRoom, Set<IEntity>> reversed;
    private PropertyChangeSupport propertySupport;

    // CONSTRUCTEUR
    private EntityPositionKeeper() {
        positions = new HashMap<>();
        reversed = new HashMap<>();
        propertySupport = new PropertyChangeSupport(this);
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

    // REQUETES
    public IRoom getPosition(IEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        return positions.get(entity);
    }

    public Collection<IEntity> getEntities(IRoom room) {
        if (room == null) {
            throw new NullPointerException();
        }
        Set<IEntity> result = reversed.get(room);
        if (result == null) {
            result = new HashSet<>();
        }
        return result;
    }

    // COMMANDES
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
        propertySupport.firePropertyChange(ROOM_PROPERTY, old, room);
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
        propertySupport.firePropertyChange(ROOM_PROPERTY, null, position);
    }

    public void deleteEntity(IEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        IRoom room = positions.remove(entity);
        reversed.get(room).remove(entity);
        propertySupport.firePropertyChange(ROOM_PROPERTY, null, room);
        propertySupport.firePropertyChange(REMOVE_ENTITY_PROPERTY, null, entity);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener l) {
        if (l == null) {
            throw new AssertionError("l'écouteur est null");
        }
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.addPropertyChangeListener(property, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.removePropertyChangeListener(l);
    }
}
