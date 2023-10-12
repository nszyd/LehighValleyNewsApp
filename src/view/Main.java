package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = new VBox(10);  // Changed to VBox for vertical arrangement and added spacing
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(10));

            // Header
            Label headerLabel = new Label("Lehigh Valley News Hub");
            headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");  // Styling the header

            // Filters and Search
            HBox filtersSearchBox = new HBox(10);  // Added spacing
            filtersSearchBox.setAlignment(Pos.CENTER);
            ComboBox<String> filtersComboBox = new ComboBox<>();
            filtersComboBox.getItems().addAll("Filter 1", "Filter 2", "Filter 3");  // Example filters
            TextField searchBar = new TextField();
            searchBar.setPromptText("Search");
            filtersSearchBox.getChildren().addAll(filtersComboBox, searchBar);

            // Posts
            HBox postsBox = new HBox(10);  // Added spacing
            postsBox.setAlignment(Pos.CENTER);
            for (int i = 1; i <= 4; i++) {
                VBox postCard = createPostCard(i);
                postsBox.getChildren().add(postCard);
            }

            root.getChildren().addAll(headerLabel, filtersSearchBox, postsBox);

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lehigh Valley News App");  // Set title
            primaryStage.show();

            System.out.println("Lehigh Valley News App Program");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createPostCard(int postNumber) {
        VBox postCard = new VBox(10);  // Added spacing
        postCard.setAlignment(Pos.CENTER);
        postCard.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");  // Border style

        Label titleLabel = new Label("Article Name " + postNumber);
        titleLabel.setStyle("-fx-font-weight: bold;");

        ImageView imageView = new ImageView(new Image("file:placeholder.png"));  // Placeholder image
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label authorLabel = new Label("Author Name");
        Label newsCompanyLabel = new Label("News Company");
        Label dateLabel = new Label("Date");

        postCard.getChildren().addAll(titleLabel, imageView, authorLabel, newsCompanyLabel, dateLabel);
        return postCard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
