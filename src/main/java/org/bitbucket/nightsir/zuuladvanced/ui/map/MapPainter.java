package org.bitbucket.nightsir.zuuladvanced.ui.map;

import org.bitbucket.nightsir.zuuladvanced.model.Direction;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Abstraction for MapPainters.
 * 
 * @author Christian Sami
 */
abstract class MapPainter {
	protected static final double LINE_WIDTH = 2;
	
	protected static final double ROOM_SIZE = 0.75;
	protected static final double ROOM_PADDING = 0.125;
	protected static final double EXIT_OFFSET = 0.05;
	protected static final double NAME_TEXT_PADDING = 0.02;
	protected static final String NAME_TEXT_FONT_FAMILY = "monospace";
	protected static final double NAME_TEXT_FONT_SIZE = 20;
	
	protected static final Font NAME_TEXT_FONT = Font.font(NAME_TEXT_FONT_FAMILY, FontWeight.NORMAL, NAME_TEXT_FONT_SIZE);
	
	protected static final Color ROOM_COLOR = Color.BLACK;
	protected static final Color NAME_TEXT_COLOR = Color.BLACK;
	protected static final LinearGradient EXIT_GRADIENT_BR_TO_TL = new LinearGradient(0, 0, 1, 1, true,
            CycleMethod.REFLECT,
            new Stop(0.0, ROOM_COLOR),
            new Stop(1.0, Color.TRANSPARENT));
	protected static final LinearGradient EXIT_GRADIENT_TL_TO_BR = new LinearGradient(1, 1, 0, 0, true,
            CycleMethod.REFLECT,
            new Stop(0.0, ROOM_COLOR),
            new Stop(1.0, Color.TRANSPARENT));
	
	protected GraphicsContext map;
	protected double width;
	protected double height;
	
	/**
	 * Creates a Painter with a given canvas.
	 * 
	 * @param mapCanvas canvas to paint at
	 */
	protected MapPainter(Canvas mapCanvas) {
		this.map = mapCanvas.getGraphicsContext2D();
		this.width = mapCanvas.getWidth();
		this.height = mapCanvas.getHeight();
		
		map.setLineWidth(LINE_WIDTH);
	}
	
	/**
	 * Clears and set the background of the set canvas to the given color.
	 * 
	 * @param backgroundColor color to set it to
	 */
	protected void clearAndSetBackground(Color backgroundColor) {
		map.clearRect(0, 0, width, height);
		map.setFill(backgroundColor);
		map.fillRect(0, 0, width, height);
	}
	
	private void translate(double x, double y) {
		map.translate(x, y);
	}
	
	private void restore(double x, double y) {
		translate(-x, -y);
	}
	
	/**
	 * Paints a room at a given position with a given dimension.
	 * 
	 * @param x position to paint
	 * @param y position to paint
	 * @param width of the room
	 * @param height of the room
	 */
	protected void paintRoom(double x, double y, double width, double height) {
		translate(x, y);
		map.setStroke(ROOM_COLOR);
		map.strokeRect(width * ROOM_PADDING, height * ROOM_PADDING, width * ROOM_SIZE, height * ROOM_SIZE);
		restore(x, y);
	}
	
	/**
	 * Paints a exit for a direction for a room at a given position with a given dimension. 
	 * 
	 * @param neighbourDirection direction to paint the exit for
	 * @param x position to paint
	 * @param y position to paint
	 * @param width of the room
	 * @param height of the room
	 * @param doubleLength if the length should be double or single sized
	 */
	protected void paintExit(Direction neighbourDirection, double x, double y, double width, double height, boolean doubleLength) {
		translate(x, y);
		if (Direction.NORTH.equals(neighbourDirection)) {
			map.setStroke(EXIT_GRADIENT_TL_TO_BR);
			map.strokeLine(width * (0.5 - EXIT_OFFSET), doubleLength ? -height * ROOM_PADDING : 0, width * (0.5 - EXIT_OFFSET), height * ROOM_PADDING);
			map.strokeLine(width * (0.5 + EXIT_OFFSET), doubleLength ? -height * ROOM_PADDING : 0, width * (0.5 + EXIT_OFFSET), height * ROOM_PADDING);
		} else if (Direction.EAST.equals(neighbourDirection)) {
			map.setStroke(EXIT_GRADIENT_BR_TO_TL);
			map.strokeLine(width * (1 - ROOM_PADDING), height * (0.5 - EXIT_OFFSET), doubleLength ? width * (1 + ROOM_PADDING) : width, height * (0.5 - EXIT_OFFSET));
			map.strokeLine(width * (1 - ROOM_PADDING), height * (0.5 + EXIT_OFFSET), doubleLength ? width * (1 + ROOM_PADDING) : width, height * (0.5 + EXIT_OFFSET));
		} else if (Direction.SOUTH.equals(neighbourDirection)) {
			map.setStroke(EXIT_GRADIENT_BR_TO_TL);
			map.strokeLine(width * (0.5 - EXIT_OFFSET), height * (1 - ROOM_PADDING), width * (0.5 - EXIT_OFFSET), doubleLength ? height * (1 + ROOM_PADDING) : height);
			map.strokeLine(width * (0.5 + EXIT_OFFSET), height * (1 - ROOM_PADDING), width * (0.5 + EXIT_OFFSET), doubleLength ? height * (1 + ROOM_PADDING) : height);
		} else if (Direction.WEST.equals(neighbourDirection)) {
			map.setStroke(EXIT_GRADIENT_TL_TO_BR);
			map.strokeLine(doubleLength ? -width * ROOM_PADDING : 0, height * (0.5 - EXIT_OFFSET), width * ROOM_PADDING, height * (0.5 - EXIT_OFFSET));
			map.strokeLine(doubleLength ? -width * ROOM_PADDING : 0, height * (0.5 + EXIT_OFFSET), width * ROOM_PADDING, height * (0.5 + EXIT_OFFSET));
		}
		restore(x, y);
	}
	
	/**
	 * Paints a text in the middle of a room at a given position with a given dimension.
	 * 
	 * @param text to write
	 * @param x position to paint
	 * @param y position to paint
	 * @param width of the room
	 * @param height of the room
	 */
	protected void paintText(String text, double x, double y, double width, double height) {
		translate(x, y);
		map.setFill(NAME_TEXT_COLOR);
		map.setFont(NAME_TEXT_FONT);
		map.fillText(text, width * (ROOM_PADDING + NAME_TEXT_PADDING), height * (ROOM_PADDING + NAME_TEXT_PADDING) + NAME_TEXT_FONT_SIZE, width * (ROOM_SIZE - 2*NAME_TEXT_PADDING));
		restore(x, y);
	}
}
