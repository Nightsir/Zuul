package org.bitbucket.nightsir.zuuladvanced.ui.map;

import java.util.ArrayList;
import java.util.List;

import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.model.room.Room;
import org.bitbucket.nightsir.zuuladvanced.model.room.RoomLoader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Painter for the WorldMap.
 * 
 * @author Christian Sami
 */
public class WorldMapPainter extends MapPainter {
	private static final Color WORLDMAP_BACKGROUND_COLOR = Color.WHITE;
	
	private static final Color CURRENT_POSITION_CIRCLE_COLOR = Color.RED;
	private static final double CURRENT_POSITION_CIRCLE_SIZE = 20;
	private static final double CURRENT_POSITION_CIRCLE_PADDING = 0.02;
	
	private static final double NUMBER_OF_HORIZONTAL_ROOMS = 5;
	private static final double NUMBER_OF_VERTICAL_ROOMS = 5;
	private static final int STARTING_ROOM_HORIZONTAL_OFFSET = 2;
	private static final int STARTING_ROOM_VERTICAL_OFFSET = 2;
	
	private static WorldMapPainter instance;
	
	private double roomWidth;
	private double roomHeight;
	private List<String> paintedRooms;
	
	private WorldMapPainter(Canvas mapCanvas) {
		super(mapCanvas);
		roomWidth = width / NUMBER_OF_HORIZONTAL_ROOMS;
		roomHeight = height / NUMBER_OF_VERTICAL_ROOMS;
	}
	
	/**
	 * Gets the instance of the {@link WorldMapPainter}.
	 * The painter has to be initialized first otherwise null will be returned.
	 * 
	 * @see #initialize(Canvas)
	 * 
	 * @return worldmappainter-instance or <code>null</code> if not initialized
	 */
	public static WorldMapPainter get() {
		return instance;
	}
	
	/**
	 * Initializes the {@link WorldMapPainter} with the canvas to paint at.
	 * 
	 * @param mapCanvas canvas to paint the map at
	 */
	public static void initialize(Canvas mapCanvas) {
		instance = new WorldMapPainter(mapCanvas);
	}
	
	private void paintRoom(String roomId, double offsetX, double offsetY) {
		if (GameState.get().isRoomVisited(roomId) && !paintedRooms.contains(roomId) &&
				offsetX >= 0 && offsetX <= NUMBER_OF_HORIZONTAL_ROOMS &&
				offsetY >= 0 && offsetY <= NUMBER_OF_VERTICAL_ROOMS) {
			Room room = RoomLoader.get().getRoom(roomId);
			
			paintRoom(offsetX * roomWidth, offsetY * roomHeight, roomWidth, roomHeight);
			paintText(room.getName(), offsetX * roomWidth, offsetY * roomHeight, roomWidth, roomHeight);
			
			paintedRooms.add(roomId);
			
			room.getNeighbourPositions().forEach((d) -> paintExitCalculateOffsetAndPaintNextRoom(room, d, offsetX, offsetY));
		}
	}
	
	private void paintExitCalculateOffsetAndPaintNextRoom(Room room, Direction direction, double offsetX, double offsetY) {
		paintExit(direction, offsetX * roomWidth, offsetY * roomHeight, roomWidth, roomHeight, true);
		if (Direction.NORTH.equals(direction)) {
			paintRoom(room.getNeighbour(direction).get().getRoomBehind(), offsetX, offsetY - 1);
		} else if (Direction.EAST.equals(direction)) {
			paintRoom(room.getNeighbour(direction).get().getRoomBehind(), offsetX + 1, offsetY);
		} else if (Direction.SOUTH.equals(direction)) {
			paintRoom(room.getNeighbour(direction).get().getRoomBehind(), offsetX, offsetY + 1);
		} else if (Direction.WEST.equals(direction)) {
			paintRoom(room.getNeighbour(direction).get().getRoomBehind(), offsetX - 1, offsetY);
		}
	}
	
	/**
	 * Paints all visited rooms to the set canvas.
	 */
	public void paintVisitedRooms() {
		paintedRooms = new ArrayList<>();
		
		clearAndSetBackground(WORLDMAP_BACKGROUND_COLOR);
		paintRoom(GameState.get().getCurrentRoomId(), STARTING_ROOM_HORIZONTAL_OFFSET, STARTING_ROOM_VERTICAL_OFFSET);
		
		map.setFill(CURRENT_POSITION_CIRCLE_COLOR);
		map.fillOval(roomWidth * (1 + STARTING_ROOM_HORIZONTAL_OFFSET - ROOM_PADDING - CURRENT_POSITION_CIRCLE_PADDING) - CURRENT_POSITION_CIRCLE_SIZE,
				roomHeight * (1 + STARTING_ROOM_VERTICAL_OFFSET - ROOM_PADDING - CURRENT_POSITION_CIRCLE_PADDING) - CURRENT_POSITION_CIRCLE_SIZE,
				CURRENT_POSITION_CIRCLE_SIZE, CURRENT_POSITION_CIRCLE_SIZE);
	}
}
