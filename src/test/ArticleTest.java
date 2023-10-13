package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Article;
import model.Author;

class ArticleTest {

    @Test
    public void testIsFromCompany() {
        Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
        Article article = new Article("Test Title", author, "2023-10-12", "http://example.com", "Example Corp", "Test Content");
        assertTrue(article.isFromCompany("Example Corp"));
        assertFalse(article.isFromCompany("Other Corp"));
    }

    @Test
    public void testGetContentSnippet() {
        Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
        Article article = new Article("Test Title", author, "2023-10-12", "http://example.com", "Example Corp", "Test Content");
        assertEquals("Test Con", article.getContentSnippet(8));
        assertEquals("Test Content", article.getContentSnippet(50));
    }

    @Test
    public void testIsWrittenBy() {
        Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
        Article article = new Article("Test Title", author, "2023-10-12", "http://example.com", "Example Corp", "Test Content");
        assertTrue(article.isWrittenBy("John Doe"));
        assertFalse(article.isWrittenBy("Jane Doe"));
    }
}
