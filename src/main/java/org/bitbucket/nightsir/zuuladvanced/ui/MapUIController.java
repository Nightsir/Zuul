package org.bitbucket.nightsir.zuuladvanced.ui;

import org.bitbucket.nightsir.zuuladvanced.ui.layoutfix.CanvasSizeFixController;
import org.bitbucket.nightsir.zuuladvanced.ui.map.WorldMapPainter;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

/**
 * Controller for the WorldMap-window.
 * 
 * @author Christian Sami
 */
public class MapUIController implements CanvasSizeFixController {
	@FXML
	private BorderPane rootPane;
	@FXML
	private Canvas map;

	@FXML
	private void initialize() {
		correctSizeOfCanvas(rootPane, map);

		WorldMapPainter.initialize(map);
		WorldMapPainter.get().paintVisitedRooms();
	}
}
