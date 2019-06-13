package org.bitbucket.nightsir.zuuladvanced.state;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.model.item.ItemList;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.model.item.types.Key;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Class to save the state of the player inventory.
 * 
 * @author Christian Sami
 */
public class InventoryState {
	private static InventoryState instance;
	
	private Integer wallet;
	private ItemList items;
	private Set<Item> nonDropableItems;
	
	private InventoryState() {
		wallet = 0;
		items = new ItemList();
		nonDropableItems = new HashSet<>();
	}
	
	/**
	 * Getter for the Singleton instance of {@link InventoryState}.
	 * 
	 * @return instance of {@link InventoryState}
	 */
	public static InventoryState get() {
		if (instance == null) {
			instance = new InventoryState();
		}
		return instance;
	}
	
	/**
	 * Removes a given amount of money from the wallet.
	 * 
	 * @param amount to remove
	 * @return <code>true</code> if it was possible to remove the amount - <code>false</code> otherwise
	 */
	public boolean pay(Integer amount) {
		if (wallet >= amount) {
			wallet -= amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds a given amount of money to the wallet.
	 * 
	 * @param amount to add
	 */
	public void income(Integer amount) {
		wallet += amount;
	}
	
	/**
	 * Gets the current amount of money in the wallet.
	 * 
	 * @return amount of money in wallet
	 */
	public Integer getWallet() {
		return wallet;
	}
	
	/**
	 * Removes a given count of an item from the inventory.
	 * 
	 * @param itemName name of item to remove
	 * @param count to remove
	 * @return count of actually removed items
	 */
	public int removeAmountOfItem(String itemName, Integer count) {
		int total = items.getCountOfItem(itemName);
		if (total == 0) {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_NOT_IN_INVENTORY);
			count = 0;
		} else if (count > total) {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_NOT_ENOUGHT_IN_INVENTORY);
			count = 0;
		} else {
			items.removeItem(itemName, count);
		}
		return count;
	}
	
	/**
	 * Removes the current count of an item from the inventory.
	 * 
	 * @param itemName name of item to remove
	 * @return count of actually removed items
	 */
	public int removeAllOfItem(String itemName) {
		return removeAmountOfItem(itemName, items.getCountOfItem(itemName));
	}
	
	/**
	 * Adds a given count of an item to the inventory.
	 * 
	 * @param itemName name of item to remove
	 * @param count to remove
	 */
	public void addAmountOfItem(String itemName, Integer count) {
		Item item = ItemLoader.get().getItem(itemName);
		
		if (item instanceof Key) {
			nonDropableItems.add(item);
		} else {
			items.addItem(itemName, count);
		}
	}
	
	/**
	 * Gets the description of an item corresponding to a given name in the player inventory.
	 * 
	 * @param itemName name of the item to get the description for
	 * @return description of the item or {@link Optional#empty()} if the item was not found
	 */
	public Optional<String> getItemDescriptionIfInInventory(String itemName) {
		if (items.contains(itemName)) {
			return Optional.ofNullable(ItemLoader.get().getItem(itemName).getDescription());
		} else {
			Optional<Item> item = nonDropableItems.stream().filter((i) -> i.getName().equals(itemName)).findAny();
			if (item.isPresent()) {
				return Optional.ofNullable(item.get().getDescription());
			} else {
				return Optional.empty();
			}
		}
	}
	
	/**
	 * Checks if a item is in the player inventory.
	 * 
	 * @param itemName name of the item to check
	 * @return <code>true</code> if it is in the inventory - <code>false</code> otherwise
	 */
	public boolean isItemInInventory(String itemName) {
		return items.contains(itemName) || nonDropableItems.contains(itemName);
	}
	
	/**
	 * Checks if the key for a given room is in the inventory.
	 * 
	 * @param roomId id of the room to check
	 * @return <code>true</code> if a key for the room is in the inventory - <code>false</code> otherwise
	 */
	public boolean isKeyForRoomInInventory(String roomId) {
		return nonDropableItems.stream()
				.filter((i) -> i instanceof Key)
				.anyMatch((k) -> ((Key)k).canOpenRoom(roomId));
	}
	
	/**
	 * Gets a {@link Stream} of dropable items in the inventory.
	 * 
	 * @return stream of dropable items
	 */
	public Stream<String> getDropableItemNames() {
		return getItemNameWithCount().map(Entry::getKey);
	}
	
	/**
	 * Gets a {@link Stream} of items in the inventory with their corresponding count.
	 * 
	 * @return stream of items with its count
	 */
	public Stream<Entry<String, Integer>> getItemNameWithCount() {
		return items.getItemNameWithCount();
	}
	
	/**
	 * Gets a {@link Stream} of non-dropable items in the inventory.
	 * 
	 * @return stream of non-dropable items
	 */
	public Stream<Item> getNonDropableItems() {
		return nonDropableItems.stream();
	}
}
