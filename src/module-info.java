module LehighValleyNewsApp {
	requires javafx.controls;
	requires junit;
	requires org.junit.jupiter.api;
	requires org.jsoup;
	exports model;
	
	opens view to javafx.graphics, javafx.fxml;
}
