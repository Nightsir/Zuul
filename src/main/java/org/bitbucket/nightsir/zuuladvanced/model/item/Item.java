package org.bitbucket.nightsir.zuuladvanced.model.item;

/**
 * An abstract representation of an item obejct.<br>
 * The item object holds the name and the description of an item.
 * Two item are equals if they have the same name.
 * 
 * @author Christian Sami
 */
public abstract class Item {
	private String name;
	private String description;
	
	/**
	 * Constructor for an item object.
	 * 
	 * @param name of the item
	 * @param description of the item
	 */
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the name of this item.
	 * 
	 * @return item name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description of this item.
	 * 
	 * @return item description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
