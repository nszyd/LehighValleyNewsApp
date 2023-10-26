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

public class Scraper {

    private static final String URL = "https://sauconsource.com/";
    private static final String COMPANY_NAME = "SauconSource";

    public List<Article> scrapeLatestArticles() throws IOException {
        List<Article> articles = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();
        Elements latestArticles = doc.select("article");  // Selecting each article element

        for (Element articleElement : latestArticles) {
            String title = articleElement.select("h2.entry-title a").text();
            String articleUrl = articleElement.select("h2.entry-title a").attr("href");
            String imageUrl = articleElement.select("div.td-module-image img").attr("src");
            String authorName = articleElement.select("span.td-post-author-name a").text();
            String date = articleElement.select("time.entry-date").attr("datetime");

            // Splitting the author's full name into first and last name assuming there's no middle name
            String[] nameParts = authorName.split(" ");
            Author author = new Author(nameParts[0], nameParts.length > 1 ? nameParts[1] : "", null, COMPANY_NAME);
            Article article = new Article(title, author, date, articleUrl, COMPANY_NAME, null);
            article.setImage(imageUrl);

            articles.add(article);
        }

        return articles;
    }
}
