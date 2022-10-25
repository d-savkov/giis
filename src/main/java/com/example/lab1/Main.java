package com.example.lab1;

import com.example.lab1.controllers.MainController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
		fxmlLoader.setResources(ResourceBundle.getBundle("/locale/locale", new Locale("ru")));
		Parent fxmlMain = fxmlLoader.load();

		MainController controller = fxmlLoader.getController();
		controller.setMainStage(primaryStage);

		primaryStage.setWidth(1200);
		primaryStage.setHeight(600);
		primaryStage.setResizable(false);

		primaryStage.setTitle(fxmlLoader.getResources().getString("GIIS"));
		primaryStage.setScene(new Scene(fxmlMain));
		primaryStage.show();
	}
}
