package org.bitbucket.nightsir.zuuladvanced.model.room;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.model.item.ItemList;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacterList;

/**
 * Representation for a room.<br>
 * All rooms are identified by an id, have a name and a description.
 * Also there are all direct neighbour-rooms with the corresponding direction saved.
 * A Room can also be secret and contain items or/and NPCs.
 * 
 * @author Christian Sami
 */
public class Room {
	private String id;
	private String name;
	private String description;
	private Map<Direction, Optional<Door>> neighbours;
	private boolean secretRoom;
	private ItemList items;
	private NonPlayerCharacterList npcs;
	
	/**
	 * Creates a non secret room with given id, name and description.
	 * 
	 * @see #Room(String, String, String, boolean)
	 * 
	 * @param id of the room
	 * @param name of the room
	 * @param description of the room
	 */
	public Room(String id, String name, String description) {
		this(id, name, description, false);
	}
	
	/**
	 * Creates a room with given id, name and description and the possibility to be secret.
	 * 
	 * @param id of the room
	 * @param name of the room
	 * @param description of the room
	 * @param secretRoom if the room is secret
	 */
	public Room(String id, String name, String description, boolean secretRoom) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.secretRoom = secretRoom;
		
		this.neighbours = new HashMap<>();
		this.items = new ItemList();
		this.npcs = new NonPlayerCharacterList();
	}
	
	/**
	 * Gets the door to the neighbour in the given direction.
	 * 
	 * @param direction you want the door for
	 * @return door in that direction or {@link Optional#empty()} if no door exists in that direction
	 */
	public Optional<Door> getNeighbour(Direction direction) {
		if (neighbours.containsKey(direction)) {
			return neighbours.get(direction);
		}
		return Optional.empty();
	}
	
	/**
	 * Gets a {@link Stream} of all {@link Direction} where there is a neighbour.
	 * 
	 * @return stream of direction of neighbour
	 */
	public Stream<Direction> getNeighbourPositions() {
		return neighbours.entrySet().stream().filter(
				(e) -> e.getValue().isPresent()).map((e) -> e.getKey());
	}
	
	/**
	 * Sets the doors for all four neighbours.<br>
	 * If there is no neighbour in one direction you should use {@link Optional#empty()}.
	 * 
	 * @param northDoor door for neighbour to north
	 * @param eastDoor door for neighbour to east
	 * @param southDoor door for neighbour to south
	 * @param westDoor door for neighbour to west
	 */
	public void setNeighbours(Optional<Door> northDoor, Optional<Door> eastDoor, Optional<Door> southDoor, Optional<Door> westDoor) {
		neighbours.put(Direction.NORTH, northDoor);
		neighbours.put(Direction.EAST, eastDoor);
		neighbours.put(Direction.SOUTH, southDoor);
		neighbours.put(Direction.WEST, westDoor);
	}
	
	/**
	 * Gets the id of the room.
	 * 
	 * @return id of the room
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the name of the room.
	 * 
	 * @return name of the room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the description of the room.
	 * 
	 * @return description of the room
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Checks if the room is secret.
	 * 
	 * @return if the room is secret
	 */
	public boolean isSecretRoom() {
		return secretRoom;
	}
	
	/**
	 * Gets the list containing the items contained in the room.
	 * 
	 * @return list of item contained in the room
	 */
	public ItemList getItems() {
		return items;
	}
	
	/**
	 * Gets the list containing the NPCs contained in the room.
	 * 
	 * @return list of NPC contained in the room
	 */
	public NonPlayerCharacterList getNpcs() {
		return npcs;
	}
}