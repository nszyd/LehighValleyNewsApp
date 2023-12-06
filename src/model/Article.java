package model;

import java.io.Serializable;

//This class encapsulates details about an article, such as title, author, and content.
public class Article implements Displayable, Serializable {
    private String title; // Title of the article
    private Author author; // Author object representing the article's author
    private String date; // Publication date of the article
    private String url; // URL of the article
    private String image; // URL of the image associated with the article
    private String company; // Company or publisher of the article
    private String content; // Content of the article
    private boolean isManuallyAdded; // Flag to indicate if the article was added manually

// Constructor to initialize an Article with its attributes.
    public Article(String title, Author author, String date, String url, String company, String content, boolean isManuallyAdded) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
        this.company = company;
        this.content = content;
        this.isManuallyAdded = isManuallyAdded;
    }

    // Getter methods for retrieving article attributes
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public String getDate() { return date; }
    public String getUrl() { return url; }
    public String getImage() { return image; }
    public String getCompany() { return company; }
    public String getContent() { return content; }

    public boolean isManuallyAdded() { return isManuallyAdded; }

    // Setter methods for updating article attributes
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(Author author) { this.author = author; }
    public void setDate(String date) { this.date = date; }
    public void setUrl(String url) { this.url = url; }
    public void setImage(String image) { this.image = image; }
    public void setCompany(String company) { this.company = company; }
    public void setContent(String content) { this.content = content; }

    public void setManuallyAdded(boolean isManuallyAdded) { this.isManuallyAdded = isManuallyAdded; }

    // Additional methods for specific functionalities

    //Check if article is from a certain company
    public boolean isFromCompany(String company) {
        return this.company.equals(company);
    }

    //Get a snippet of content
    public String getContentSnippet(int snippetLength) {
        return content.substring(0, Math.min(snippetLength, content.length()));
    }

    //Check if the article is written by an author
    public boolean isWrittenBy(String authorFullName) {
        return author.fullName().equals(authorFullName);
    }

    @Override
    public void display() {
        System.out.println("Title: " + this.title);
        System.out.println("Content: " + getContentSnippet(100));
        System.out.println("Date: " + this.date);
    }

}
