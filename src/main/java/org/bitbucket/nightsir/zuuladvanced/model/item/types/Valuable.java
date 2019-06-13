package org.bitbucket.nightsir.zuuladvanced.model.item.types;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;
import org.bitbucket.nightsir.zuuladvanced.model.item.Sellable;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemPropertyConstructor;

/**
 * Class for a valuable object.
 * 
 * @author Christian Sami
 * 
 * @see Item
 */
@ItemPropertyConstructor({String.class, String.class, String.class})
public class Valuable extends Item implements Sellable {
	private int value;

	/**
	 * Normal constructor for a valuable object.
	 * 
	 * @see #Valuable(String, String, String)
	 * 
	 * @param name of the valuable
	 * @param description of the valuable
	 * @param value of the valuable
	 */
	public Valuable(String name, String description, int value) {
		super(name, description);
		this.value = value;
	}
	
	/**
	 * Factory constructor for a valuable object.
	 * 
	 * @see #Valuable(String, String, int)
	 * 
	 * @param name of the valuable
	 * @param description of the valuable
	 * @param value of the valuable
	 * @throws NumberFormatException - if the given value is a non numberic String
	 */
	public Valuable(String name, String description, String value) throws NumberFormatException {
		this(name, description, Integer.parseInt(value));
	}
	
	@Override
	public int getValue() {
		return value;
	}
}
