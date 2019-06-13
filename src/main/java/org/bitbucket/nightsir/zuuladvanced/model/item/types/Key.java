package org.bitbucket.nightsir.zuuladvanced.model.item.types;

import java.util.Arrays;
import java.util.List;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemPropertyConstructor;

/**
 * Class for a key object.<br>
 * Keys are used for opening doors to a room,
 * so they have the information about which rooms they can open.
 * 
 * @author Christian Sami
 * 
 * @see Item
 */
@ItemPropertyConstructor({String.class, String.class, String.class})
public class Key extends Item {
	private List<String> possibleRooms;
	
	/**
	 * Normal constructor for a key object.
	 * 
	 * @see #Key(String, String, String)
	 * 
	 * @param name of the key
	 * @param description of the key
	 * @param possibleRooms id of rooms the key can open
	 */
	public Key(String name, String description, String... possibleRooms) {
		super(name, description);
		this.possibleRooms = Arrays.asList(possibleRooms);
	}
	
	/**
	 * Factory constructor for a key object.<br>
	 * Look at the other constructor for the normal one.
	 * 
	 * @see #Key(String, String, String[])
	 * 
	 * @param name of the key
	 * @param description of the key
	 * @param possibleRooms id of rooms the key can open separated by comma
	 */
	public Key(String name, String description, String possibleRooms) {
		this(name, description, possibleRooms.split(","));
	}
	
	/**
	 * Checks if this key can open a given room.
	 * 
	 * @param roomId id of the room to check
	 * @return if this key can open the room
	 */
	public boolean canOpenRoom(String roomId) {
		return possibleRooms.contains(roomId);
	}
}
