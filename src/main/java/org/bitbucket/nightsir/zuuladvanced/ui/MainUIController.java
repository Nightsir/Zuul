package org.bitbucket.nightsir.zuuladvanced.ui;

import org.bitbucket.nightsir.zuuladvanced.command.Command;
import org.bitbucket.nightsir.zuuladvanced.command.SystemCommand;
import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.controller.StreamController;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;
import org.bitbucket.nightsir.zuuladvanced.ui.layoutfix.CanvasSizeFixController;
import org.bitbucket.nightsir.zuuladvanced.ui.map.MiniMapPainter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Controller for the main-window of the application.
 * 
 * @author Christian Sami
 */
public class MainUIController implements CanvasSizeFixController {
	@FXML
	private GridPane rootPane;
	@FXML
	private TextArea output;
	@FXML
	private TextField input;
	@FXML
	private Canvas miniMap;
	@FXML
	private TextArea inventory;
	@FXML
	private TextArea noDropInventory;
	
	@FXML
    private void initialize() {
		rootPane.addEventFilter(KeyEvent.KEY_RELEASED, (keyEvent) -> processKeyRelease(keyEvent));
		correctSizeOfCanvas(rootPane, miniMap);
		
		StreamController.changeSystemOutTo(output);
		MiniMapPainter.initialize(miniMap);
		FeedbackState.get().addFeedbackAction(FeedbackState.GAME_INTRO);
		FeedbackState.get().addFeedbackAction(FeedbackState.ROOM_INFO);
		
		updateEverything();
    }
	
	@FXML
	private void processInput(ActionEvent actionEvent) {
		String inputText = input.getText();
		input.clear();
		output.appendText(inputText + "\n");
		
		Command.executeCommandIfExisting(inputText.toLowerCase().trim().replaceAll("\\s+", " "));
		updateEverything();
	}
	
	private void updateEverything() {
		MiniMapPainter.get().repaintMap();
		updateInventory();
		FeedbackState.get().invoke();
		output.appendText(Configuration.get("sym_input"));
	}
	
	private void updateInventory() {
		inventory.setText(Configuration.get("txt_wallet") + InventoryState.get().getWallet() + "\n");
		inventory.appendText(Configuration.get("txt_inv"));
		InventoryState.get().getItemNameWithCount()
				.map((e) -> "\n" + e.getKey() + "\n\t" + Configuration.get("txt_inv_amount") + e.getValue())
				.forEach(inventory::appendText);
		inventory.positionCaret(0);
		
		noDropInventory.setText(Configuration.get("txt_no_drop_inv") + "\n");
		InventoryState.get().getNonDropableItems().map(Item::getName).reduce((a, b) -> a + "," + b).ifPresent(noDropInventory::appendText);
		noDropInventory.positionCaret(0);
	}
	
	private void processKeyRelease(KeyEvent keyEvent) {
		switch (keyEvent.getCode()) {
			case F3:
				if (SystemCommand.changeDeveloperMode()) {
					System.out.println("\n" + Configuration.get("dev_start"));
				} else {
					System.out.println("\n" + Configuration.get("dev_exit"));
				}
				break;
			case F10:
				if (SystemCommand.startMonitoring()) {
					System.out.println("\n" + Configuration.get("dev_monitor"));
				} else {
					return;
				}
				break;
			default:
				//return early, so event will not be consumed
				return;
		}
		keyEvent.consume();
		output.appendText(Configuration.get("sym_input"));
	}
}
