package model;

import java.io.Serializable;

// Represents a User entity which extends the Entity class and is serializable
public class User extends Entity implements Serializable {
    private Data data; // Instance variable to hold user's data
    private Post post; // Instance variable to hold user's post

    // Constructor to initialize User with Data and Post objects
    public User(Data data, Post post) {
        this.data = data;
        this.post = post;
    }

    // Getter method to retrieve the Data object
    public Data getData() { return data; }
    // Getter method to retrieve the Post object
    public Post getPost() { return post; }

    // Setter method to update the Data object
    public void setData(Data data) { this.data = data; }
    // Setter method to update the Post object
    public void setPost(Post post) { this.post = post; }

    // Override the description method from the Entity class
    @Override
    public String description() {
        return "This is a user with post titled " + post.getArticleTitle();
    }
}