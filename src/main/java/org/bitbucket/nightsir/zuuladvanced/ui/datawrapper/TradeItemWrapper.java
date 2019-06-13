package org.bitbucket.nightsir.zuuladvanced.ui.datawrapper;

import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.model.npc.types.returntypes.TradeItem;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * UI wrapper for a {@link TradeItem}.
 * 
 * @author Christian Sami
 */
public class TradeItemWrapper {
	private String itemId;
	private StringProperty itemNameProperty;
	private ObjectProperty<Integer> valueProperty;
	
	/**
	 * Constructs a new {@link TradeItemWrapper}.
	 * 
	 * @see #TradeItemWrapper(TradeItem)
	 * 
	 * @param itemId id of the item
	 * @param itemName name of the item
	 * @param value value of the item
	 */
	public TradeItemWrapper(String itemId, String itemName, Integer value) {
		this.itemId = itemId;
		this.itemNameProperty = new SimpleStringProperty(itemName);
		this.valueProperty = new SimpleObjectProperty<>(value);
	}
	
	/**
	 * Constructs a new {@link TradeItemWrapper} out of a {@link TradeItem}
	 * 
	 * @see #TradeItemWrapper(String, String, Integer)
	 * 
	 * @param tradeItem to wrap for the ui
	 */
	public TradeItemWrapper(TradeItem tradeItem) {
		this(tradeItem.getItemId(), ItemLoader.get().getItemNameForId(tradeItem.getItemId()), tradeItem.getValue());
	}
	
	/**
	 * Converts the {@link TradeItemWrapper} back to a {@link TradeItem}.
	 * 
	 * @return the corresponding trade-item
	 */
	public TradeItem getTradeItem() {
		return new TradeItem(itemId, getValue());
	}
	
	/**
	 * Gets name a String.
	 * 
	 * @return name
	 */
	public String getItemName() {
		return itemNameProperty.get();
	}
	
	/**
	 * Sets name to a given one.
	 * 
	 * @param itemName name to set
	 */
	public void setItemName(String itemName) {
		this.itemNameProperty.set(itemName);
	}
	
	/**
	 * Gets the string-property of the name.
	 * 
	 * @return name property
	 */
	public StringProperty itemNameProperty() {
		return itemNameProperty;
	}
	
	/**
	 * Gets value as Integer.
	 * 
	 * @return value
	 */
	public Integer getValue() {
		return valueProperty.get();
	}
	
	/**
	 * Sets value to a given one.
	 * 
	 * @param value to set
	 */
	public void setValue(Integer value) {
		this.valueProperty.set(value);
	}
	
	/**
	 * Gets the integer-property of the value.
	 * 
	 * @return value property
	 */
	public ObjectProperty<Integer> valueProperty() {
		return valueProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNameProperty == null) ? 0 : itemNameProperty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeItemWrapper other = (TradeItemWrapper) obj;
		if (itemNameProperty == null) {
			if (other.itemNameProperty != null)
				return false;
		} else if (!itemNameProperty.get().equals(other.itemNameProperty.get()))
			return false;
		return true;
	}
}
