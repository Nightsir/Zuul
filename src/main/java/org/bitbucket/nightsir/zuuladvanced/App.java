package org.bitbucket.nightsir.zuuladvanced;

import org.bitbucket.nightsir.zuuladvanced.ui.controller.StreamController;
import org.bitbucket.nightsir.zuuladvanced.ui.controller.WindowController;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main-class of the application.<br>
 * Has the JavaFX {@link Application#start(Stage)} method to start the first UI.
 * Initialises the {@link StreamController} and starts the JavaFX application
 * with {@link Application#launch(String...)}.
 * 
 * @author Christian Sami
 */
public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		WindowController.get().openMainWindow(primaryStage);
		StreamController.changeSystemError();
	}
	
	/**
	 * Main method to start the whole application.
	 * 
	 * @param args
	 */
	public static void run(String[] args) {
		launch(args);
	}
}
