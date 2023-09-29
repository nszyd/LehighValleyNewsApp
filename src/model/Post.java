package model;

public class Post {
    private Article article;
    private String image;
    private Author author;
    private String company;
    private String date;
    private String url;

    public Post(Article article, String image, Author author, String company, String date, String url) {
        this.article = article;
        this.image = image;
        this.author = author;
        this.company = company;
        this.date = date;
        this.url = url;
    }

    // Getters
    public Article getArticle() { return article; }
    public String getImage() { return image; }
    public Author getAuthor() { return author; }
    public String getCompany() { return company; }
    public String getDate() { return date; }
    public String getUrl() { return url; }

    // Setters
    public void setArticle(Article article) { this.article = article; }
    public void setImage(String image) { this.image = image; }
    public void setAuthor(Author author) { this.author = author; }
    public void setCompany(String company) { this.company = company; }
    public void setDate(String date) { this.date = date; }
    public void setUrl(String url) { this.url = url; }
}