package model;

public class User {
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
}