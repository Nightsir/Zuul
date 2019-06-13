package org.bitbucket.nightsir.zuuladvanced.model.room;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.model.npc.loading.CharacterLoader;

/**
 * Class for loading {@link Room} from <i>room.properties</i>.<br>
 * The corresponding {@link Room} is saved locally but lazy loaded.
 * 
 * @author Christian Sami
 */
public class RoomLoader {
	private static final String ROOM_PROPERTIES_FILE_NAME = "room.properties";
	private static RoomLoader instance;
	
	private Properties roomProperties;
	private Map<String, Room> roomList;
	
	private RoomLoader() {
		roomList = new HashMap<>();
		
		roomProperties = new Properties();
		try {
			roomProperties.load(getClass().getClassLoader().getResourceAsStream(ROOM_PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter for the Singleton instance of {@link RoomLoader}.
	 * 
	 * @return instance of {@link RoomLoader}
	 */
	public static RoomLoader get() {
		if (instance == null) {
			instance = new RoomLoader();
		}
		return instance;
	}
	
	/**
	 * Gets the room marked as starting room in <i>room.properties</i>.
	 * 
	 * @return starting room
	 */
	public Room getStartingRoom() {
		return getRoom(roomProperties.getProperty("starting_room"));
	}
	
	private void loadRoom(String roomId) {
		String[] roomValues = roomProperties.getProperty(roomId).split(":", -1);
		
		Room room = new Room(roomId, roomValues[0], roomValues[1], Boolean.parseBoolean(roomValues[3]));
		
		Door[] exits = Arrays.asList(roomValues[2].split(",", -1)).stream()
				.map((id) -> id.isEmpty() ? null : new Door(id.replace("$", ""), id.endsWith("$"))).toArray(Door[]::new);
		
		room.setNeighbours(
				Optional.ofNullable(exits[0]),
				Optional.ofNullable(exits[1]),
				Optional.ofNullable(exits[2]),
				Optional.ofNullable(exits[3]));
		
		//load items
		if (!roomValues[4].isEmpty()) {
			String[] itemAmountPairs = roomValues[4].split(";", -1);
			for (String itemAmountPair : itemAmountPairs) {
				String[] values = itemAmountPair.split(",", 2);
				room.getItems().addItem(
						ItemLoader.get().getItemNameForId(values[0]),
						Integer.parseInt(values[1]));
			}
		}
		
		//load NPCs
		if (!roomValues[5].isEmpty()) {
			String[] npcs = roomValues[5].split(";", -1);
			for (String npc : npcs) {
				room.getNpcs().addNpc(
						CharacterLoader.get().getNPC(npc));
			}
		}
		
		roomList.put(roomId, room);
	}
	
	/**
	 * Gets a room for the corresponding id.
	 * 
	 * @param roomId id of the room
	 * @return room corresponding with the id
	 */
	public Room getRoom(String roomId) {
		if (!roomList.containsKey(roomId)) {
			loadRoom(roomId);
		}
		return roomList.get(roomId);
	}
}
