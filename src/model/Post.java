package model;

import java.io.Serializable;

// Represents a Post entity, implementing Displayable for displaying its information and Serializable for object serialization
public class Post implements Displayable, Serializable {
    private Article article; // Instance variable to hold the Article object
    private String image; // Variable for image URL associated with the post
    private Author author; // Instance variable to hold the Author object
    private String company; // Variable for the company name associated with the post
    private String date; // Variable for the date of the post
    private String url; // Variable for the URL of the post

    // Constructor to initialize Post with its attributes
    public Post(Article article, String image, Author author, String company, String date, String url) {
        this.article = article;
        this.image = image;
        this.author = author;
        this.company = company;
        this.date = date;
        this.url = url;
    }

    // Getter methods for retrieving post attributes
    public Article getArticle() { return article; }
    public String getImage() { return image; }
    public Author getAuthor() { return author; }
    public String getCompany() { return company; }
    public String getDate() { return date; }
    public String getUrl() { return url; }

    // Setter methods for updating post attributes
    public void setArticle(Article article) { this.article = article; }
    public void setImage(String image) { this.image = image; }
    public void setAuthor(Author author) { this.author = author; }
    public void setCompany(String company) { this.company = company; }
    public void setDate(String date) { this.date = date; }
    public void setUrl(String url) { this.url = url; }
    
    //Additional Methods
    
    //Get the authors full name
    public String getAuthorFullName() {
        return author.fullName();
    }
    
    
    //Get the title of the Article from the post
    public String getArticleTitle() {
        return article.getTitle();
    }
    
    //Check if an article from a post is from a certain company
    public boolean isFromCompany(String company) {
        return this.company.equals(company);
    }


    // Implement the display method from the Displayable interface
    @Override
    public void display() {
        System.out.println("Article Title: " + getArticleTitle());
        System.out.println("Image URL: " + this.image);
        System.out.println("Date: " + this.date);
    }
    
}