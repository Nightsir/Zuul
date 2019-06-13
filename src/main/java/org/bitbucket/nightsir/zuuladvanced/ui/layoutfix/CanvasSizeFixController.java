package org.bitbucket.nightsir.zuuladvanced.ui.layoutfix;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Interface with implemented methods to correct the size of a canvas.<br>
 * An interface was used to ensure the class can still be subclass of anything.
 * 
 * @author Christian Sami
 */
public interface CanvasSizeFixController {
	/**
	 * Corrects the size of a canvas in a grid-pane so that
	 * the new size of the canvas is the size of the grid-pane.
	 * 
	 * @param gridPane to change the size to
	 * @param canvas to change the size of
	 */
	default void correctSizeOfCanvas(GridPane gridPane, Canvas canvas) {
		canvas.widthProperty().set(
				(gridPane.getPrefWidth() - gridPane.getPadding().getLeft() - gridPane.getPadding().getRight() - 
						(gridPane.getColumnConstraints().size() - 1) * gridPane.getVgap()) * 
				gridPane.getColumnConstraints().get(GridPane.getColumnIndex(canvas)).getPercentWidth() / 100);
		canvas.heightProperty().set(
				(gridPane.getPrefHeight() - gridPane.getPadding().getTop() - gridPane.getPadding().getBottom() - 
						(gridPane.getRowConstraints().size() - 1) * gridPane.getHgap()) * 
				gridPane.getRowConstraints().get(GridPane.getRowIndex(canvas)).getPercentHeight() / 100);
	}
	
	/**
	 * Corrects the size of a canvas in a border-pane so that
	 * the new size of the canvas is the size of the border-pane.
	 * 
	 * @param borderPane to change the size to
	 * @param canvas to change the size of
	 */
	default void correctSizeOfCanvas(BorderPane borderPane, Canvas canvas) {
		canvas.widthProperty().set(borderPane.getPrefWidth());
		canvas.heightProperty().set(borderPane.getPrefHeight());
	}
}
