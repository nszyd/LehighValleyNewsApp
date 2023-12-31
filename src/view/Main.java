package view;

//Handle Imports
import controller.Scraper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Article;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Main application class for the Lehigh Valley News App
public class Main extends Application {
    // GUI components and state variables
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

    // Entry point of the JavaFX application
    @Override
    public void start(Stage primaryStage) {
        // Initialize and configure GUI components
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

        Button addButton = new Button("Add");
        addButton.getStyleClass().add("add-button");
        addButton.setOnAction(event -> showAddArticleDialog());
        addButton.setVisible(false); // Initially invisible


        // Event handling and interaction setup
        sessionComboBox.setOnAction(event -> {
            isSavedArticlesView = "Saved Articles".equals(sessionComboBox.getSelectionModel().getSelectedItem());
            addButton.setVisible(isSavedArticlesView); // Only show add button in "Saved Articles" view
            handleSavedArticlesSelection();
        });

        filtersSearchBox.getChildren().addAll(sessionComboBox,addButton);
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
                if (isSavedArticlesView) {
                    loadSavedArticlesView();
                } else {
                    loadArticles(true); // Pass true to indicate it should load from scraper
                }
            }
        });

        nextButton.setOnAction(event -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                if (isSavedArticlesView) {
                    loadSavedArticlesView();
                } else {
                    loadArticles(true); // Pass true to indicate it should load from scraper
                }
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
            loadArticles(true);
            adjustLayoutAfterLoading();
        });


        // Finalize and display the main window
        root.getChildren().addAll(headerLabel, filtersSearchBox, loadButton, postsContainer, navigationBox);
        navigationBox.setVisible(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lehigh Valley News App");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Adjust layout after loading articles
    private void adjustLayoutAfterLoading() {
        // Update header label and remove load button
        headerLabel.setText("Lehigh Valley News");
        headerLabel.setStyle("-fx-font-size: 30px;");

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().remove(loadButton);

        filtersSearchBox.setVisible(true);
        filtersSearchBox.setPadding(new Insets(10, 0, 0, 10));

        navigationBox.setVisible(true);
        navigationBox.setPadding(new Insets(10, 0, 0, 10));
    }

    // Load articles from scraper or saved list
    private void loadArticles(boolean fromScraper) {
        if (fromScraper) {
            // Load articles from the scraper
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
        } else {
            // Load saved articles
            loadSavedArticlesView();
        }

        // Set the visibility of navigation buttons
        prevButton.setVisible(currentPage > 0);
        nextButton.setVisible(currentPage < totalPages - 1);
        navigationBox.setVisible(true);
    }

    // Create a post card for displaying an article
    private VBox createPostCard(Article article) {

        // Construct a post card with article details
        VBox postCard = new VBox(10);
        postCard.getStyleClass().add("post-card");
        postCard.setAlignment(Pos.CENTER_LEFT);
        postCard.setEffect(new DropShadow());

        HBox titleAndButton = new HBox(10);
        titleAndButton.setAlignment(Pos.CENTER_LEFT);

        //Determine if article is already saved
        Button actionButton = new Button();
        boolean isArticleSaved = savedArticles.stream().anyMatch(savedArticle ->
                savedArticle.getUrl().equals(article.getUrl()) &&
                        savedArticle.getTitle().equals(article.getTitle()) &&
                        savedArticle.getDate().equals(article.getDate()));
        //Dynamically set the symbol
        if (isSavedArticlesView) {
            actionButton.setText("X");
            actionButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 5;");
            actionButton.setOnAction(event -> {
                removeArticleFromSaved(article);
                loadSavedArticlesView();
            });
        } else {
            if (isArticleSaved) {
                actionButton.setText("✓");
                actionButton.setDisable(true);
            } else {
                actionButton.setText("+");
                actionButton.setOnAction(event -> {
                    saveArticle(article);
                    actionButton.setText("✓");
                    actionButton.setDisable(true);
                });
            }
            actionButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-background-radius: 5;");
        }
        //Make the cards hyperlinks
        Hyperlink titleLink = new Hyperlink(article.getTitle());
        titleLink.getStyleClass().add("post-title-link");
        titleLink.setOnAction(event -> getHostServices().showDocument(article.getUrl()));

        titleAndButton.getChildren().addAll(actionButton, titleLink);

        Label detailsLabel = new Label(article.getCompany() + " | " + article.getDate());
        detailsLabel.getStyleClass().add("post-details-label");
    //Provide ability to edit if the card was manually added
        if (article.isManuallyAdded()) {
            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-background-radius: 5;");
            editButton.setOnAction(event -> showEditArticleDialog(article));
            titleAndButton.getChildren().add(editButton);
        }


        postCard.getChildren().addAll(titleAndButton, detailsLabel);

        return postCard;
    }
    // Save an article to the savedArticles list
    private void saveArticle(Article article) {
        // Check if the article is already in the savedArticles list
        boolean isAlreadySaved = savedArticles.stream().anyMatch(savedArticle ->
                savedArticle.getUrl().equals(article.getUrl()) &&
                        savedArticle.getTitle().equals(article.getTitle()) &&
                        savedArticle.getDate().equals(article.getDate()));

        if (!isAlreadySaved) {
            savedArticles.add(article);
            serializeArticles();
        }
    }
    // Remove an article from the saved list
    private void removeArticleFromSaved(Article article) {
        // Confirm before deletion and remove the article if confirmed
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this article?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                savedArticles.remove(article);
                serializeArticles();
                handleSavedArticlesSelection();
            }
        });
    }

    // Display a dialog for editing an existing article
    private void showEditArticleDialog(Article article) {
        // Create the custom dialog.
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Edit Article");
        dialog.initModality(Modality.APPLICATION_MODAL); // Block input to other windows

        // Set the button types.
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Create the title, url, company, and date fields with pre-filled data.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField title = new TextField(article.getTitle());
        TextField url = new TextField(article.getUrl());
        TextField company = new TextField(article.getCompany());
        DatePicker datePublished = new DatePicker(LocalDate.parse(article.getDate()));

        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("URL:"), 0, 1);
        grid.add(url, 1, 1);
        grid.add(new Label("News Company:"), 0, 2);
        grid.add(company, 1, 2);
        grid.add(new Label("Date Published:"), 0, 3);
        grid.add(datePublished, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the title field by default.
        Platform.runLater(title::requestFocus);

        // Convert the result to an article when the update button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Article(title.getText(), null, datePublished.getValue().toString(), url.getText(), company.getText(), null, true);
            }
            return null;
        });

        Optional<Article> result = dialog.showAndWait();

        result.ifPresent(newArticleData -> {
            // Update the article with new data
            article.setTitle(newArticleData.getTitle());
            article.setUrl(newArticleData.getUrl());
            article.setCompany(newArticleData.getCompany());
            article.setDate(newArticleData.getDate());
            serializeArticles();
            if (isSavedArticlesView) {
                handleSavedArticlesSelection();
            }
        });
    }

    // Display a dialog for adding a new article
    private void showAddArticleDialog() {
        // Create the custom dialog.
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Add New Article");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the title, url, company, and date fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField title = new TextField();
        title.setPromptText("Title");
        TextField url = new TextField();
        url.setPromptText("URL");
        TextField company = new TextField();
        company.setPromptText("News Company");
        DatePicker datePublished = new DatePicker();

        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("URL:"), 0, 1);
        grid.add(url, 1, 1);
        grid.add(new Label("News Company:"), 0, 2);
        grid.add(company, 1, 2);
        grid.add(new Label("Date Published:"), 0, 3);
        grid.add(datePublished, 1, 3);

        // Enable/Disable save button depending on whether a title was entered.
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Validation for the title input.
        title.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the title field by default.
        Platform.runLater(title::requestFocus);

        // Convert the result to an article when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Article(title.getText(), null, datePublished.getValue().toString(), url.getText(), company.getText(), null, true);
            }
            return null;
        });

        Optional<Article> result = dialog.showAndWait();

        result.ifPresent(article -> {
            savedArticles.add(article);
            serializeArticles();
            if (isSavedArticlesView) {
                handleSavedArticlesSelection();
            }
        });
    }

    // Serialize and save the list of articles to a file
    private void serializeArticles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
            out.writeObject(savedArticles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Load saved articles from a file
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

    // Load and display saved articles in the view
    private void loadSavedArticlesView() {
        // Clear previous articles
        postsContainer.getChildren().clear();

        // Calculate the range of articles to display
        int start = currentPage * 4;
        int end = Math.min((currentPage + 1) * 4, savedArticles.size());

        // Update totalPages for saved articles
        totalPages = (int) Math.ceil((double) savedArticles.size() / 4);

        // Add articles to the container
        for (int i = start; i < end; i++) {
            postsContainer.getChildren().add(createPostCard(savedArticles.get(i)));
        }

        // Update the visibility of navigation buttons
        prevButton.setVisible(currentPage > 0);
        nextButton.setVisible(end < savedArticles.size());
        navigationBox.setVisible(totalPages > 1); // Show only if there's more than one page
    }


    // Handle selection changes in the session combo box
    private void handleSavedArticlesSelection() {
        // Switch between "Current Session" and "Saved Articles" views
        String selected = sessionComboBox.getSelectionModel().getSelectedItem();
        isSavedArticlesView = "Saved Articles".equals(selected);

        if (isSavedArticlesView) {
            currentPage = 0; // Reset to the first page for saved articles
            loadSavedArticlesView();
        } else {
            currentPage = 0; // Reset to the first page for current session
            loadArticles(true); // Load articles from the scraper
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}