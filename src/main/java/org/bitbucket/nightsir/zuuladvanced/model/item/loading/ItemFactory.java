package org.bitbucket.nightsir.zuuladvanced.model.item.loading;

import java.lang.reflect.Constructor;

import org.bitbucket.nightsir.zuuladvanced.model.item.Item;

/**
 * Factory for {@link Item}
 * 
 * @author Christian Sami
 */
public class ItemFactory {
	private static final String CLASSPATH = "org.bitbucket.nightsir.zuuladvanced.model.item.types.";

	/**
	 * Creates a new Instance for a {@link Item} subclass.</br></br>
	 * 
	 * The given itemValues which are used for the construction should be in the same order as the
	 * values in the constructor specified in the class given.</br>
	 * There should be an only {@link String}-Type constructor in the given class, which is specified
	 * with the annotation {@link ItemPropertyConstructor}
	 * 
	 * @param itemTypeClassName	name of the class to instantiate
	 * @param itemValues values for the item in order (name, description, itemSpecificModifiers)
	 * @return newly constructed Item
	 */
	public static Item createItemFor(String itemTypeClassName, String... itemValues) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Item> itemTypeClass = (Class<? extends Item>) Class.forName(CLASSPATH + itemTypeClassName);

			Class<?>[] parameterTypes = itemTypeClass.getAnnotation(ItemPropertyConstructor.class).value();
			Constructor<? extends Item> constructor = itemTypeClass.getConstructor(parameterTypes);
			return constructor.newInstance(itemValues);
		} catch (ReflectiveOperationException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
