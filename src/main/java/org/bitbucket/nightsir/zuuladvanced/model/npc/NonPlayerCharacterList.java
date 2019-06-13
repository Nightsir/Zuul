package org.bitbucket.nightsir.zuuladvanced.model.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * List of NPC.
 * 
 * @author Christian Sami
 */
public class NonPlayerCharacterList {
	private List<NonPlayerCharacter> npcList;
	
	/**
	 * Constructs a new List.
	 */
	public NonPlayerCharacterList() {
		npcList = new ArrayList<>();
	}
	
	/**
	 * Adds a NPC to the list.
	 * 
	 * @param nonPlayerCharacter to add
	 */
	public void addNpc(NonPlayerCharacter nonPlayerCharacter) {
		npcList.add(nonPlayerCharacter);
	}
	
	/**
	 * Gets a NPC by its name.
	 * 
	 * @param name to search for
	 * @return NPC for given name or {@link Optional#empty()} if not found
	 */
	public Optional<NonPlayerCharacter> getNpcByName(String name) {
		return npcList.stream().filter((c) -> c.getName().equalsIgnoreCase(name)).findFirst();
	}
	
	/**
	 * Checks if the list is empty
	 * 
	 * @return if list is empty
	 */
	public boolean isEmpty() {
		return npcList.isEmpty();
	}
	
	/**
	 * Gets a {@link Stream} of the NPCs contained in the list
	 * 
	 * @return stream of contained NPC
	 */
	public Stream<NonPlayerCharacter> getNpcStream() {
		return npcList.stream();
	}
}
