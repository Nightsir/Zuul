package org.bitbucket.nightsir.zuuladvanced.model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * List for items. This class saves only the item name and
 * the times the item is in the list.
 * 
 * @author Christian Sami
 */
public class ItemList {
	private Map<String, Integer> items;
	
	/**
	 * Constructs a new ItemList
	 */
	public ItemList() {
		items = new HashMap<>();
	}
	
	/**
	 * Gets how many times the given item is in the list.
	 * 
	 * @param itemName name of the item
	 * @return count of the item
	 */
	public int getCountOfItem(String itemName) {
		return items.getOrDefault(itemName, 0);
	}
	
	/**
	 * Adds a item once to the list.<br>
	 * For adding an item multiple times use the method with count.
	 * 
	 * @see #addItem(String, int)
	 * 
	 * @param itemName name of the item to add
	 */
	public void addItem(String itemName) {
		addItem(itemName, 1);
	}
	
	/**
	 * Adds an item a given times to the list.<br>
	 * For adding an item just once use the method without count.
	 * 
	 * @see #addItem(String)
	 * 
	 * @param itemName name of the item to add
	 * @param count number of times you want to add the item
	 */
	public void addItem(String itemName, int count) {
		items.put(itemName, count + items.getOrDefault(itemName, 0));
	}
	
	/**
	 * Removes an item once from the list.<br>
	 * For removing an item multiple times use the method with count.
	 * 
	 * @see #removeItem(String, int)
	 * 
	 * @param itemName name of the item
	 */
	public void removeItem(String itemName) {
		removeItem(itemName, 1);
	}
	
	/**
	 * Removes an item a given times for the list.<br>
	 * If the count you want to remove it is greater than
	 * the actual count all items will be removed.<br>
	 * For removing an item just once use the method without count.
	 * 
	 * @param itemName name of the item
	 * @param count number of times you want to remove the item
	 */
	public void removeItem(String itemName, int count) {
		int newItemCount = getCountOfItem(itemName) - count;
		if (newItemCount > 0) {
			items.put(itemName, newItemCount);
		} else {
			items.remove(itemName);
		}
	}
	
	/**
	 * Removes an item completely for the list regardless of the count.
	 * 
	 * @param itemName name of the item to remove
	 */
	public void removeAllItem(String itemName) {
		items.remove(itemName);
	}
	
	/**
	 * Checks if the list is empty.
	 * 
	 * @return <code>true</code> if no item is in the list - <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	/**
	 * Checks if the list contains an item.
	 * 
	 * @param itemName name of the item to check
	 * @return <code>true</code> if the item is in the list - <code>false</code> otherwise
	 */
	public boolean contains(String itemName) {
		return items.containsKey(itemName);
	}
	
	/**
	 * Gets a {@link Stream} of {@link Entry} with the item names and the corresponding counts.
	 * 
	 * @return stream of item/count pairs
	 */
	public Stream<Entry<String, Integer>> getItemNameWithCount() {
		return items.entrySet().stream();
	}
}
