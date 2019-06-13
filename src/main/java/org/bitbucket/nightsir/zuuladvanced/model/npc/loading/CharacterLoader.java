package org.bitbucket.nightsir.zuuladvanced.model.npc.loading;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.Invocable;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;
import org.bitbucket.nightsir.zuuladvanced.script.ScriptFactory;

/**
 * Class for loading {@link NonPlayerCharacter} from <i>npc.properties</i>.<br>
 * The corresponding {@link NonPlayerCharacter} is saved locally but lazy loaded.
 * 
 * @author Christian Sami
 */
public class CharacterLoader {
	private static final String NPC_PROPERTIES_FILE_NAME = "npc.properties";
	private static CharacterLoader instance;
	
	private Properties npcProperties;
	private Map<String, NonPlayerCharacter> npcList;
	
	private CharacterLoader() {
		npcList = new HashMap<>();
		
		npcProperties = new Properties();
		try {
			npcProperties.load(getClass().getClassLoader().getResourceAsStream(NPC_PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter for the Singleton instance of {@link CharacterLoader}.
	 * 
	 * @return instance of {@link CharacterLoader}
	 */
	public static CharacterLoader get() {
		if (instance == null) {
			instance = new CharacterLoader();
		}
		return instance;
	}
	
	private void loadNPC(String npcId) {
		String[] npcCreationValues = npcProperties.getProperty(npcId).split(":", 4);
		
		Invocable logic = ScriptFactory.convertJSStringToInvocable(npcCreationValues[3]);
		npcList.put(npcId,
				CharacterFactory.createCharacterFor(
						npcCreationValues[0],
						npcCreationValues[1],
						logic,
						npcCreationValues[2]));
	}
	
	/**
	 * Gets a NPC by the corresponding id.<br>
	 * If the NPC is loaded it will be returned,
	 * otherwise the NPC will be newly loaded.
	 * 
	 * @param npcId id of the NPC to get
	 * @return NPC corresponding to the given id
	 */
	public NonPlayerCharacter getNPC(String npcId) {
		if (!npcList.containsKey(npcId)) {
			loadNPC(npcId);
		}
		return npcList.get(npcId);
	}
}
