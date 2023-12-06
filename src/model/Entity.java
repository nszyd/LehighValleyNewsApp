package model;

import java.io.Serializable;

// Superclass Entity
//Represents the base class (superclass) for entities in the model.
public class Entity implements Serializable {
    public String description() {
        return "This is a general entity.";
    }
}
