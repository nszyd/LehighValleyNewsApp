package model;

import java.io.Serializable;

//Represents a Data entity within the model.
public class Data implements Serializable {
    private Post post;

    public Data(Post post) {
        this.post = post;
    }

    // Getter
    public Post getPost() { return post; }

    // Setter
    public void setPost(Post post) { this.post = post; }
}