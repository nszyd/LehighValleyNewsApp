package view;

import controller.Scraper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Article;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private HBox postsBox;

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = new VBox(10);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(10));

            Label headerLabel = new Label("Lehigh Valley News Hub");
            headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            HBox filtersSearchBox = new HBox(10);
            filtersSearchBox.setAlignment(Pos.CENTER);
            ComboBox<String> filtersComboBox = new ComboBox<>();
            filtersComboBox.getItems().addAll("Filter 1", "Filter 2", "Filter 3");
            TextField searchBar = new TextField();
            searchBar.setPromptText("Search");
            filtersSearchBox.getChildren().addAll(filtersComboBox, searchBar);

            postsBox = new HBox(10);
            postsBox.setAlignment(Pos.CENTER);

            Button loadButton = new Button("Load");
            loadButton.setOnAction(event -> loadArticles());

            root.getChildren().addAll(headerLabel, filtersSearchBox, loadButton, postsBox);

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lehigh Valley News App");
            primaryStage.show();

            System.out.println("Lehigh Valley News App Program");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadArticles() {
        Scraper scraper = new Scraper();
        try {
            List<Article> articles = scraper.scrapeLatestArticles();
            postsBox.getChildren().clear();  // Clear existing post cards
            
            int count = 0;  // Initialize a count variable
            for (Article article : articles) {
                if (count >= 4) {  // Break the loop if 4 articles have already been added
                    break;
                }
                VBox postCard = createPostCard(article);
                postsBox.getChildren().add(postCard);
                count++;  // Increment the count for each article added
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load articles.");
            alert.showAndWait();
        }
    }


    private VBox createPostCard(Article article) {
        VBox postCard = new VBox(10);
        postCard.setAlignment(Pos.CENTER);
        postCard.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");

        Label titleLabel = new Label(article.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold;");

        ImageView imageView;
        if (article.getImage() == null || article.getImage().isEmpty()) {
            imageView = new ImageView(new Image("file:placeholder.png"));  // Placeholder image
        } else {
            imageView = new ImageView(new Image(article.getImage()));
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label authorLabel = new Label(article.getAuthor().fullName());
        Label newsCompanyLabel = new Label(article.getCompany());
        Label dateLabel = new Label(article.getDate());

        postCard.getChildren().addAll(titleLabel, imageView, authorLabel, newsCompanyLabel, dateLabel);
        return postCard;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
