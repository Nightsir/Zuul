package org.bitbucket.nightsir.zuuladvanced.model.npc.loading;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import javax.script.Invocable;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.ActionArgumentInterceptor;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.AllowedNPCActionTypes;

/**
 * Factory for {@link NonPlayerCharacter}.
 * 
 * @author Christian Sami
 */
public class CharacterFactory {
	private static final String CLASSPATH = "org.bitbucket.nightsir.zuuladvanced.model.npc.types.";
	
	/**
	 * Creates a new Instance for a {@link NonPlayerCharacter} subclass.</br></br>
	 * Instances are wrapped into {@link Proxy} for intercepting invoke calls.
	 * 
	 * @param itemTypeClassName	name of the class to instantiate
	 * @param itemValues values for the item in order (name, description, itemSpecificModifiers)
	 * 
	 * @param npcTypeClassName name of the class to instantiate
	 * @param npcName name value for the NPC
	 * @param npcLogic logic for the NPC
	 * @param additionalInformation with semicolon separated to create the NPC
	 * @return newly constructed NPC
	 */
	public static NonPlayerCharacter createCharacterFor(String npcTypeClassName, String npcName, Invocable npcLogic, String additionalInformation) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends NonPlayerCharacter> npcTypeClass = (Class<? extends NonPlayerCharacter>) Class.forName(CLASSPATH + npcTypeClassName);

			Class<?>[] parameterTypes = {String.class, Invocable.class};
			Object[] parameterValues = new Object[] {npcName, npcLogic};
			
			if (!additionalInformation.isEmpty()) {
				for (String additional : additionalInformation.split(";")) {
					int newLength = parameterTypes.length + 1;
					
					parameterTypes = Arrays.copyOf(parameterTypes, newLength);
					parameterTypes[newLength - 1] = String.class;
					
					parameterValues = Arrays.copyOf(parameterValues, newLength);
					parameterValues[newLength - 1] = additional;
				}
			}
			
			Constructor<? extends NonPlayerCharacter> constructor = npcTypeClass.getConstructor(parameterTypes);
			NonPlayerCharacter npc = constructor.newInstance(parameterValues);
			
			Class<?>[] interfaces = npcTypeClass.getInterfaces();
			if (interfaces.length == 0) {
				interfaces = new Class[]{NonPlayerCharacter.class};
			}
			
			return (NonPlayerCharacter) Proxy.newProxyInstance(
					npcTypeClass.getClassLoader(),
					interfaces,
					new ActionArgumentInterceptor(npc, npcTypeClass.getAnnotation(AllowedNPCActionTypes.class).value()));
		} catch (ReflectiveOperationException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
