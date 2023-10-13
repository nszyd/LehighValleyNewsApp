module LehighValleyNewsApp {
	requires javafx.controls;
	requires junit;
	requires org.junit.jupiter.api;
	exports model;
	
	opens view to javafx.graphics, javafx.fxml;
}
