package org.bitbucket.nightsir.zuuladvanced.ui.map;

import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.model.room.Room;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Painter for the MiniMap.
 * 
 * @author Christian Sami
 */
public class MiniMapPainter extends MapPainter {
	protected static final Color MINIMAP_BACKGROUND_COLOR = Color.WHITE;
	
	private static MiniMapPainter instance;
	
	private MiniMapPainter(Canvas mapCanvas) {
		super(mapCanvas);
	}
	
	/**
	 * Gets the instance of the {@link MiniMapPainter}.
	 * The painter has to be initialized first otherwise null will be returned.
	 * 
	 * @see #initialize(Canvas)
	 * 
	 * @return minimappainter-instance or <code>null</code> if not initialized
	 */
	public static MiniMapPainter get() {
		return instance;
	}
	
	/**
	 * Initializes the {@link MiniMapPainter} with the canvas to paint at.
	 * 
	 * @param mapCanvas canvas to paint the map at
	 */
	public static void initialize(Canvas mapCanvas) {
		instance = new MiniMapPainter(mapCanvas);
	}
	
	/**
	 * Repaints the minimap on the set canvas.
	 */
	public void repaintMap() {
		Room currentRoom = GameState.get().getCurrentRoom();
		
		clearAndSetBackground(MINIMAP_BACKGROUND_COLOR);
		paintRoom(0, 0, width, height);
		
		currentRoom.getNeighbourPositions().forEach(this::paintExit);
		
		paintText(currentRoom.getName(), 0, 0, width, height);
	}
	
	private void paintExit(Direction neighbourDirection) {
		paintExit(neighbourDirection, 0, 0, width, height, false);
	}
}
