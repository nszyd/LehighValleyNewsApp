package view;
import controller.Scraper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private VBox root;
    private VBox postsContainer;
    private Label headerLabel;
    private HBox filtersSearchBox;
    private ComboBox<String> sessionComboBox;
    private List<Article> savedArticles;
    private static final String SAVE_PATH = "savedArticles.ser";
    private Button loadButton;
    private HBox navigationBox;
    private int currentPage = 0;
    private int totalPages;
    private Button prevButton;
    private Button nextButton;
    private boolean isSavedArticlesView = false;
    @Override
    public void start(Stage primaryStage) {
        root = new VBox(10);
        root.getStyleClass().add("root");
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        headerLabel = new Label("Lehigh Valley News Hub");
        headerLabel.getStyleClass().add("header-label");

        filtersSearchBox = new HBox(10);
        filtersSearchBox.getStyleClass().add("filters-search-box");
        filtersSearchBox.setAlignment(Pos.CENTER_LEFT);

        savedArticles = new ArrayList<>();
        loadSavedArticles();
        sessionComboBox = new ComboBox<>();
        sessionComboBox.getItems().addAll("Current Session", "Saved Articles");
        sessionComboBox.getSelectionModel().selectFirst();
        sessionComboBox.setOnAction(event -> {
            isSavedArticlesView = "Saved Articles".equals(sessionComboBox.getSelectionModel().getSelectedItem());
            handleSavedArticlesSelection();
        });

        filtersSearchBox.getChildren().add(sessionComboBox);
        filtersSearchBox.setVisible(false);

        postsContainer = new VBox(20);
        postsContainer.getStyleClass().add("posts-container");
        postsContainer.setAlignment(Pos.CENTER);

        prevButton = new Button("Previous");
        nextButton = new Button("Next");
        prevButton.getStyleClass().add("navigation-button");
        nextButton.getStyleClass().add("navigation-button");
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
        navigationBox.getStyleClass().add("navigation-box");
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(prevButton, nextButton);
        navigationBox.setVisible(false);

        loadButton = new Button("View The News");
        loadButton.getStyleClass().add("load-button");
        loadButton.setOnAction(event -> {
            loadArticles();
            adjustLayoutAfterLoading();
        });

        root.getChildren().addAll(headerLabel, filtersSearchBox, loadButton, postsContainer, navigationBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lehigh Valley News App");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void adjustLayoutAfterLoading() {
        headerLabel.setText("Lehigh Valley News");
        headerLabel.setStyle("-fx-font-size: 30px;");

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().remove(loadButton);

        filtersSearchBox.setVisible(true);
        filtersSearchBox.setPadding(new Insets(10, 0, 0, 10));

        navigationBox.setVisible(true);
        navigationBox.setPadding(new Insets(10, 0, 0, 10));
    }

    private void loadArticles() {
        Scraper scraper = new Scraper();
        try {
            List<Article> articles = scraper.scrapeLatestArticles();
            postsContainer.getChildren().clear();

            int maxArticlesToDisplay = Math.min(articles.size() - currentPage * 4, 4);
            for (int i = currentPage * 4; i < currentPage * 4 + maxArticlesToDisplay; i++) {
                Article article = articles.get(i);
                VBox postCard = createPostCard(article);
                postsContainer.getChildren().add(postCard);
            }

            totalPages = (int) Math.ceil((double) articles.size() / 4);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load articles.");
            alert.showAndWait();
        }
    }


    private VBox createPostCard(Article article) {
        VBox postCard = new VBox(10);
        postCard.getStyleClass().add("post-card");
        postCard.setAlignment(Pos.CENTER_LEFT);
        postCard.setEffect(new DropShadow());

        HBox titleAndButton = new HBox(10);
        titleAndButton.setAlignment(Pos.CENTER_LEFT);

        Button actionButton = new Button(isSavedArticlesView ? "X" : "+");
        actionButton.setStyle("-fx-background-color: " + (isSavedArticlesView ? "red" : "green") + "; -fx-text-fill: white; -fx-background-radius: 5;");
        actionButton.setOnAction(event -> {
            if (isSavedArticlesView) {
                removeArticleFromSaved(article);
            } else {
                saveArticle(article);
            }
        });

        Hyperlink titleLink = new Hyperlink(article.getTitle());
        titleLink.getStyleClass().add("post-title-link");
        titleLink.setOnAction(event -> getHostServices().showDocument(article.getUrl()));

        titleAndButton.getChildren().addAll(actionButton, titleLink);

        Label detailsLabel = new Label(article.getCompany() + " | " + article.getDate());
        detailsLabel.getStyleClass().add("post-details-label");

        postCard.getChildren().addAll(titleAndButton, detailsLabel);

        return postCard;
    }
    private void saveArticle(Article article) {
        if (!savedArticles.contains(article)) {
            savedArticles.add(article);
            serializeArticles();
        }
    }

    private void removeArticleFromSaved(Article article) {
        savedArticles.remove(article);
        serializeArticles();
        handleSavedArticlesSelection();
    }

    private void serializeArticles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
            out.writeObject(savedArticles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadSavedArticles() {
        File file = new File(SAVE_PATH);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                savedArticles = (List<Article>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSavedArticlesSelection() {
        String selected = sessionComboBox.getSelectionModel().getSelectedItem();
        postsContainer.getChildren().clear();
        if ("Saved Articles".equals(selected)) {
            savedArticles.forEach(article -> postsContainer.getChildren().add(createPostCard(article)));
        } else {
            loadArticles();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
