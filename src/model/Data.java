package model;

public class Data {
    private Post post;

    public Data(Post post) {
        this.post = post;
    }

    // Getter
    public Post getPost() { return post; }

    // Setter
    public void setPost(Post post) { this.post = post; }
}