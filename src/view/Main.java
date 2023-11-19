package view;
import controller.Scraper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Article;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private VBox root;
    private HBox postsContainer;
    private Label headerLabel;
    private HBox filtersSearchBox;
    private Button loadButton;
    private HBox navigationBox;
    private int currentPage = 0; // Track the current page
    private int totalPages; // Total number of pages
    private Button prevButton;
    private Button nextButton;

    @Override
    public void start(Stage primaryStage) {
        root = new VBox(10);
        root.getStyleClass().add("root"); // Apply root style class
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        headerLabel = new Label("Lehigh Valley News Hub");
        headerLabel.getStyleClass().add("header-label"); // Apply header style class

        filtersSearchBox = new HBox(10);
        filtersSearchBox.getStyleClass().add("filters-search-box"); // Apply filters box style class
        filtersSearchBox.setAlignment(Pos.CENTER_LEFT);
        ComboBox<String> filtersComboBox = new ComboBox<>();
        filtersComboBox.getStyleClass().add("combo-box"); // Apply combo box style class
        filtersComboBox.getItems().addAll("Filter 1", "Filter 2", "Filter 3");
        TextField searchBar = new TextField();
        searchBar.getStyleClass().add("text-field"); // Apply text field style class
        searchBar.setPromptText("Search");
        filtersSearchBox.getChildren().addAll(filtersComboBox, searchBar);
        filtersSearchBox.setVisible(false);

        postsContainer = new HBox(20);
        postsContainer.getStyleClass().add("posts-container"); // Apply posts container style class
        postsContainer.setAlignment(Pos.CENTER);

        prevButton = new Button("Previous");
        nextButton = new Button("Next");
        prevButton.getStyleClass().add("navigation-button"); // Apply navigation button style class
        nextButton.getStyleClass().add("navigation-button"); // Apply navigation button style class
        prevButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadArticles();
            }
        });

        nextButton.setOnAction(event -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                loadArticles();
            }
        });

        navigationBox = new HBox(10);
        navigationBox.getStyleClass().add("navigation-box"); // Apply navigation box style class
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(prevButton, nextButton);
        navigationBox.setVisible(false);

        loadButton = new Button("View The News");
        loadButton.getStyleClass().add("load-button"); // Apply load button style class
        loadButton.setOnAction(event -> {
            loadArticles();
            adjustLayoutAfterLoading();
        });

        root.getChildren().addAll(headerLabel, filtersSearchBox, loadButton, postsContainer, navigationBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lehigh Valley News App");
        primaryStage.setResizable(false); // Restrict window resizing
        primaryStage.show();
    }

    private void adjustLayoutAfterLoading() {
        headerLabel.setText("Lehigh Valley News");
        headerLabel.setStyle("-fx-font-size: 30px;");

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().remove(loadButton);

        filtersSearchBox.setVisible(true);
        filtersSearchBox.setPadding(new Insets(10, 0, 0, 10));

        navigationBox.setVisible(true); // Show navigation box when needed
        navigationBox.setPadding(new Insets(10, 0, 0, 10));
    }

    private void loadArticles() {
        Scraper scraper = new Scraper();
        try {
            List<Article> articles = scraper.scrapeLatestArticles();
            postsContainer.getChildren().clear();

            int maxArticlesToDisplay = Math.min(articles.size() - currentPage * 4, 4); // Limit to 4 articles or fewer
            for (int i = currentPage * 4; i < currentPage * 4 + maxArticlesToDisplay; i++) {
                Article article = articles.get(i);
                VBox postCard = createPostCard(article);
                postsContainer.getChildren().add(postCard);
            }

            // Calculate total pages
            totalPages = (int) Math.ceil((double) articles.size() / 4);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load articles.");
            alert.showAndWait();
        }
    }

    private VBox createPostCard(Article article) {
        VBox postCard = new VBox(10);
        postCard.getStyleClass().add("post-card"); // Apply post card style class
        postCard.setAlignment(Pos.CENTER);
        postCard.setEffect(new DropShadow()); // Add drop shadow effect

        Label titleLabel = new Label(article.getTitle());
        titleLabel.getStyleClass().add("post-title-label"); // Apply post title label style class

        ImageView imageView;
        if (article.getImage() == null || article.getImage().isEmpty()) {
            imageView = new ImageView(new Image("file:placeholder.png"));
        } else {
            imageView = new ImageView(new Image(article.getImage()));
        }
        imageView.getStyleClass().add("post-image"); // Apply post image style class
        imageView.setFitWidth(100); // Reduced image width
        imageView.setFitHeight(75); // Reduced image height

        Label authorLabel = new Label(article.getAuthor().fullName());
        Label newsCompanyLabel = new Label(article.getCompany());
        Label dateLabel = new Label(article.getDate());
        authorLabel.getStyleClass().add("post-details-label"); // Apply post details label style class
        newsCompanyLabel.getStyleClass().add("post-details-label"); // Apply post details label style class
        dateLabel.getStyleClass().add("post-details-label"); // Apply post details label style class

        postCard.getChildren().addAll(titleLabel, imageView, authorLabel, newsCompanyLabel, dateLabel);
        return postCard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
