package org.bitbucket.nightsir.zuuladvanced.model.item.loading;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;

/**
 * Class for loading {@link Item} from <i>item.properties</i>.<br>
 * The corresponding {@link Item} is not saved locally and would be
 * loaded multiple times if called multiple times.<br>
 * The class has also the possibility for changing from an item name
 * to the corresponding item id and the other way around.
 * 
 * @author Christian Sami
 */
public class ItemLoader {
	private static final String ITEM_PROPERTIES_FILE_NAME = "item.properties";
	private static ItemLoader instance;
	
	private Properties itemProperties;
	private Map<String, String> itemKeyMapping;

	private ItemLoader() {
		itemProperties = new Properties();
		try {
			itemProperties.load(getClass().getClassLoader().getResourceAsStream(ITEM_PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializeKeyMapping();
	}
	
	/**
	 * Getter for the Singleton instance of {@link ItemLoader}.
	 * 
	 * @return instance of {@link ItemLoader}
	 */
	public static ItemLoader get() {
		if (instance == null) {
			instance = new ItemLoader();
		}
		return instance;
	}
	
	private void initializeKeyMapping() {
		itemKeyMapping = new HashMap<>();
		
		itemProperties.forEach((k, v) -> itemKeyMapping.put(((String)v).split(":", 3)[1], (String)k));
	}
	
	/**
	 * Get the item name which corresponds with the given item id.
	 * 
	 * @param itemId id of the item you want the name for
	 * @return name of the item with the given id
	 */
	public String getItemNameForId(String itemId) {
		return itemKeyMapping.entrySet().stream()
				.filter((e) -> e.getValue().equals(itemId))
				.map(Entry::getKey).findFirst().get();
	}
	
	/**
	 * Get the item id which corresponds with the given item name.
	 * 
	 * @param itemName name of the item you want the id for
	 * @return id of the item with the given name
	 */
	public String getItemIdForName(String itemName) {
		return itemKeyMapping.entrySet().stream()
				.filter((e) -> e.getKey().equals(itemName))
				.map(Entry::getValue).findFirst().get();
	}
	
	/**
	 * Get the correct case written item name for a given name.<br>
	 * For example if <i>saPPhire</i> is given and the correct case would be
	 * <i>Sapphire</i> it will be converted.
	 * 
	 * @param itemName
	 * @return
	 */
	public String getCorrectItemName(String itemName) {
		return itemKeyMapping.keySet().stream()
				.filter((n) -> n.equalsIgnoreCase(itemName))
				.findFirst().orElse("");
	}
	
	/**
	 * Load and get the {@link Item} with the corresponding name.<br>
	 * Returned is a correct item-type and not just an {@link Item} instance.
	 * 
	 * @param itemName name of the item to load
	 * @return item for the given name
	 */
	public Item getItem(String itemName) {
		String[] itemValues = itemProperties.getProperty(itemKeyMapping.get(itemName)).split(":", -1);
		
		String type = itemValues[0];
		return ItemFactory.createItemFor(type, Arrays.copyOfRange(itemValues, 1, itemValues.length));
	}
}
