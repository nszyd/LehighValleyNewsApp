package model;

public class Article {
    private String title;
    private Author author;
    private String date;
    private String url;
    private String image;
    private String company;
    private String content;

    public Article(String title, Author author, String date, String url, String company, String content) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
        this.company = company;
        this.content = content;
    }

    // Getters
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public String getDate() { return date; }
    public String getUrl() { return url; }
    public String getImage() { return image; }
    public String getCompany() { return company; }
    public String getContent() { return content; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(Author author) { this.author = author; }
    public void setDate(String date) { this.date = date; }
    public void setUrl(String url) { this.url = url; }
    public void setImage(String image) { this.image = image; }
    public void setCompany(String company) { this.company = company; }
    public void setContent(String content) { this.content = content; }
    
    
}