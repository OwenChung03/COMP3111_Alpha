package comp3111.examsystem.tools;


import java.lang.reflect.Member;
/**
 * Represents a base entity class that can be serialized and compared.
 * This class provides an ID field for uniquely identifying entities
 * and implements the Comparable interface for ordering entities based on their IDs.
 */
public class Entity implements java.io.Serializable, Comparable<Entity> {
    protected Long id = 0L;
    /**
     * Gets the ID of the entity.
     *
     * @return the ID of the entity
     */
    public Long getId() {
        return id;
    }
    /**
     * Sets the ID of the entity.
     *
     * @param id the ID to set for the entity
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Default constructor for the Entity class.
     * Initializes a new instance of Entity with a default ID of 0.
     */
    public Entity() {
        super();
    }
    /**
     * Constructs an Entity with the specified ID.
     *
     * @param id the ID to set for the entity
     */
    public Entity(Long id) {
        super();
        this.id = id;
    }
    /**
     * Compares this entity with another entity based on their IDs.
     *
     * @param o the entity to compare with
     * @return a negative integer, zero, or a positive integer as this entity's ID
     *         is less than, equal to, or greater than the specified entity's ID
     */
    public int compareTo(Entity o) {
        return Long.compare(this.id, o.id);
    }
}
