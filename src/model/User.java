package model;

import java.io.Serializable;

public class User extends Entity implements Serializable {
    private Data data;
    private Post post;

    public User(Data data, Post post) {
        this.data = data;
        this.post = post;
    }

    // Getters
    public Data getData() { return data; }
    public Post getPost() { return post; }

    // Setters
    public void setData(Data data) { this.data = data; }
    public void setPost(Post post) { this.post = post; }
    
    
    @Override
    public String description() {
        return "This is a user with post titled " + post.getArticleTitle();
    }
}