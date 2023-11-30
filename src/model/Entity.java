package model;

import java.io.Serializable;

// Superclass Entity
public class Entity implements Serializable {
    public String description() {
        return "This is a general entity.";
    }
}
