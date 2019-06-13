package org.bitbucket.nightsir.zuuladvanced.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.model.room.Door;
import org.bitbucket.nightsir.zuuladvanced.model.room.Room;
import org.bitbucket.nightsir.zuuladvanced.model.room.RoomLoader;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Class to save the state of the game.
 * 
 * @author Christian Sami
 */
public class GameState {
	private static GameState instance;
	
	private String currentRoom;
	private List<String> visitedRooms;
	
	private GameState() {
		currentRoom = RoomLoader.get().getStartingRoom().getId();
		visitedRooms = new ArrayList<>();
		visitedRooms.add(currentRoom);
	}
	
	/**
	 * Getter for the Singleton instance of {@link GameState}.
	 * 
	 * @return instance of {@link GameState}
	 */
	public static GameState get() {
		if (instance == null) {
			instance = new GameState();
		}
		return instance;
	}
	
	/**
	 * Changes the current room to the room in the given direction.
	 * If there is no room in that direction or it is locked a feedback will be sent to {@link FeedbackState}.
	 * 
	 * @param direction to go to
	 */
	public void goToRoom(Direction direction) {
		Optional<Door> door = getCurrentRoom().getNeighbour(direction);
		
		if (door.isPresent()) {
			if (door.get().isLocked()) {
				FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_LOCKED);
			} else {
				currentRoom = RoomLoader.get().getRoom(door.get().getRoomBehind()).getId();
				if (!visitedRooms.contains(currentRoom)) {
					visitedRooms.add(currentRoom);
				}
				FeedbackState.get().addFeedbackAction(FeedbackState.ROOM_INFO);
			}
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_NOT_PRESENT);
		}
	}
	
	/**
	 * Unlocks the door of the current room in the given direction.
	 * If there is no door in that direction a feedback will be sent to {@link FeedbackState}.
	 * 
	 * @param direction
	 */
	public void unlockRoom(Direction direction) {
		Optional<Door> door = getCurrentRoom().getNeighbour(direction);
		
		if (door.isPresent()) {
			if (door.get().isLocked()) {
				if (InventoryState.get().isKeyForRoomInInventory(door.get().getRoomBehind())) {
					door.get().unlock();
					FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_UNLOCKED);
				} else {
					FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_NO_KEY);
				}
			} else {
				FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_NOT_LOCKED);
			}
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.DOOR_NOT_PRESENT);
		}
	}
	
	/**
	 * Removes a given count of an item from the current room.
	 * 
	 * @param itemName name of item to remove
	 * @param count to remove
	 * @return count of actually removed items
	 */
	public int removeAmountOfItem(String itemName, Integer count) {
		int total = getCurrentRoom().getItems().getCountOfItem(itemName);
		if (total == 0) {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_NOT_IN_ROOM);
			count = 0;
		} else if (count > total) {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_NOT_ENOUGHT_IN_ROOM);
			count = 0;
		} else {
			getCurrentRoom().getItems().removeItem(itemName, count);
		}
		return count;
	}
	
	/**
	 * Removes the current count of an item from the current room.
	 * 
	 * @param itemName name of item to remove
	 * @return count of actually removed items
	 */
	public int removeAllOfItem(String itemName) {
		return removeAmountOfItem(itemName, getCurrentRoom().getItems().getCountOfItem(itemName));
	}
	
	/**
	 * Gets the id of the current room.
	 * 
	 * @return id of current room
	 */
	public String getCurrentRoomId() {
		return currentRoom;
	}
	
	/**
	 * Gets the current room.
	 * 
	 * @return current room
	 */
	public Room getCurrentRoom() {
		return RoomLoader.get().getRoom(currentRoom);
	}
	
	/**
	 * Checks if the given room was already visited.
	 * 
	 * @param roomId id of the room to check
	 * @return <code>true</code> if it was visited - <code>false</code> otherwise
	 */
	public boolean isRoomVisited(String roomId) {
		return visitedRooms.contains(roomId);
	}
	
	/**
	 * Gets a {@link Stream} of all visited rooms.
	 * 
	 * @return stream of visited rooms.
	 */
	public Stream<String> getVisitedRooms() {
		return visitedRooms.stream();
	}
}
