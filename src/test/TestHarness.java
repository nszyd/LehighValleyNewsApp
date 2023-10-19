package test;

import model.Article;
import model.Author;
import model.Displayable;
import model.Post;
import model.User;
import model.Entity;  // Assuming User, Post, Article, etc. are in 'model' package.
import java.util.List;
import java.util.ArrayList;

public class TestHarness {

    public static void main(String[] args) {
        // Instantiate a TestHarness object, which will invoke its constructor
        // and run the tests in the constructor
        new TestHarness();
    }

    public TestHarness() {
        testInterface();
        testClassHierarchy();
        
        // Your Manual Tests
        int numFails = 0;
        
        Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
        Article article = new Article("Test Title", author, "2023-10-12", "http://example.com", "Example Corp", "Test Content");
        Post post = new Post(article, "http://example.com/image.jpg", author, "Example Corp", "2023-10-12", "http://example.com");
        
        // Test getAuthorFullName()
        if (!post.getAuthorFullName().equals("John Doe")) {
            System.out.println("getAuthorFullName() failed the test");
            numFails++;
        }
        
        // Test getArticleTitle()
        if (!post.getArticleTitle().equals("Test Title")) {
            System.out.println("getArticleTitle() failed the test");
            numFails++;
        }
        
        // Test isFromCompany(String)
        if (!post.isFromCompany("Example Corp")) {
            System.out.println("isFromCompany(String) failed the test");
            numFails++;
        }
        
        // End of Manual Test
        if (numFails == 0) {
            System.out.println("All tests pass");
        } else {
            System.out.println(numFails + " tests failed");
        }
    }
    
    public void testInterface() {
        List<Displayable> displayables = new ArrayList<>();

        displayables.add(new Article("Test Title", new Author("John", "Doe", "john.doe@example.com", "Example Corp"), "2023-10-12", "http://example.com", "Example Corp", "Test Content"));
        displayables.add(new Post(new Article("Test Title", new Author("John", "Doe", "john.doe@example.com", "Example Corp"), "2023-10-12", "http://example.com", "Example Corp", "Test Content"), "http://example.com/image.jpg", new Author("John", "Doe", "john.doe@example.com", "Example Corp"), "Example Corp", "2023-10-12", "http://example.com"));
        
        for (Displayable displayable : displayables) {
            displayable.display();
        }
    }

    public void testClassHierarchy() {
        List<Entity> entities = new ArrayList<>();
        
        entities.add(new Author("John", "Doe", "john.doe@example.com", "Example Corp"));
        entities.add(new User(null, new Post(new Article("Test Title", new Author("John", "Doe", "john.doe@example.com", "Example Corp"), "2023-10-12", "http://example.com", "Example Corp", "Test Content"), "http://example.com/image.jpg", new Author("John", "Doe", "john.doe@example.com", "Example Corp"), "Example Corp", "2023-10-12", "http://example.com")));
        
        for (Entity entity : entities) {
            System.out.println(entity.description());
        }
    }
}
