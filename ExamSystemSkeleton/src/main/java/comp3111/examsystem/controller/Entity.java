package comp3111.examsystem.controller;

import java.lang.reflect.Member;

public class Entity implements java.io.Serializable, Comparable<Entity> {
    protected Long id = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entity() {
        super();
    }

    public Entity(Long id) {
        super();
        this.id = id;
    }

    public int compareTo(Entity o) {
        return Long.compare(this.id, o.id);
    }
}

