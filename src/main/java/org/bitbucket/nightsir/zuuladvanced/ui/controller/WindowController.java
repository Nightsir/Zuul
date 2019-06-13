package org.bitbucket.nightsir.zuuladvanced.ui.controller;

import java.io.IOException;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.npc.proxytyping.AbstractTrader;
import org.bitbucket.nightsir.zuuladvanced.ui.TradeUIController;
import org.bitbucket.nightsir.zuuladvanced.ui.layoutfix.CloseableController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for constructing and opening new windows.
 * 
 * @author Christian Sami
 */
public class WindowController {
	private static WindowController instance;
	
	private WindowController() {}
	
	/**
	 * Getter for the Singleton instance of {@link WindowController}.
	 * 
	 * @return instance of {@link WindowController}
	 */
	public static WindowController get() {
		if (instance == null) {
			instance = new WindowController();
		}
		return instance;
	}
	
	private Stage getStage(String titleKey, boolean modal) {
		Stage stage = new Stage();
		
		if (modal) {
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		stage.setTitle(Configuration.get(titleKey));
		
		return stage;
	}
	
	private Object showStage(String fxmlPath, String titleKey, boolean modal) {
		Object controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();
			
			Stage stage = getStage(titleKey, modal);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			stage.show();
			
			controller = loader.getController();
			if (controller instanceof CloseableController) {
				scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ((CloseableController)controller)::onExit);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return controller;
	}
	
	/**
	 * Opens the main window of the application.
	 * 
	 * @param primaryStage to open the main node in
	 */
	public void openMainWindow(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../MainUI.fxml"));
			primaryStage.setTitle(Configuration.get("tit_main"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Opens a new modal window with the world map.
	 */
	public void openWorldMapWindow() {
		showStage("../MapUI.fxml", "tit_wrld_map", true);
	}
	
	/**
	 * Opens a new window with the monitoring functions.
	 */
	public void openMonitoringWindow() {
		showStage("../dev/ResourceMonitorUI.fxml", "tit_dev_mon", false);
	}
	
	/**
	 * Opens a new modal window with the menu to trade with a given trader.
	 * 
	 * @param trader to trade with
	 */
	public void openTradeWindow(AbstractTrader trader) {
		TradeUIController controller = (TradeUIController) showStage("../TradeUI.fxml", "tit_trade", true);
		controller.setTrader(trader);
	}
}
