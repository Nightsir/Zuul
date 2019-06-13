package org.bitbucket.nightsir.zuuladvanced.model.npc.types.returntypes;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.script.AllowedScriptClass;

/**
 * Class for {@link Item} that a trader sells/buys from the player.
 * 
 * @author Christian Sami
 */
@AllowedScriptClass
public class TradeItem {
	private String itemId;
	private Integer value;
	
	/**
	 * Constructor for a trade item.
	 * 
	 * @param itemId id of the item
	 * @param value of the item
	 */
	public TradeItem(String itemId, Integer value) {
		this.itemId = itemId;
		this.value  = value;
	}
	
	/**
	 * Gets the id of the item.
	 * 
	 * @return id of the item
	 */
	public String getItemId() {
		return itemId;
	}
	
	/**
	 * Gets the id of the item.
	 * 
	 * @return id of the item
	 */
	public Integer getValue() {
		return value;
	}
}
