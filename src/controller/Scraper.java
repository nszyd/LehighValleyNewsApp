package controller;

import model.Article;
import model.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Scraper class responsible for fetching and parsing articles from a specific website.
public class Scraper {

    // Constant for the URL of the website to scrape
    private static final String URL = "https://sauconsource.com/";
    // Constant for the company name associated with the articles
    private static final String COMPANY_NAME = "SauconSource";

    // Scrapes the latest articles from the specified URL.
    public List<Article> scrapeLatestArticles() throws IOException {
        List<Article> articles = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();
        Elements latestArticles = doc.select("article");  // Selecting each article element

        // Extracting article details from the HTML elements
        for (Element articleElement : latestArticles) {
            String title = articleElement.select("h2.entry-title a").text();
            String articleUrl = articleElement.select("h2.entry-title a").attr("href");
            String imageUrl = articleElement.select("div.td-module-image img").attr("src");
            String authorName = articleElement.select("span.td-post-author-name a").text();
            String date = articleElement.select("span.updated").text();

            // Splitting the author's full name into first and last name assuming there's no middle name
            String[] nameParts = authorName.split(" ");
            Author author = new Author(nameParts[0], nameParts.length > 1 ? nameParts[1] : "", null, COMPANY_NAME);
            // Creating an Article object with the scraped data
            Article article = new Article(title, author, date, articleUrl, COMPANY_NAME, null, false);
            article.setImage(imageUrl);

            // Adding the Article object to the list of articles
            articles.add(article);
        }

        return articles;
    }
}
