package org.bitbucket.nightsir.zuuladvanced.model.npc.types;

import javax.script.Invocable;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;

/**
 * Abstraction of a {@link NonPlayerCharacter} which saves name and logic.
 * 
 * @author Christian Sami
 */
public abstract class NPCAbstraction implements NonPlayerCharacter {
	private String name;
	private Invocable logic;

	/**
	 * Constructor for an abstract NPC.
	 * 
	 * @param name of the NPC
	 * @param logic of the NPC
	 */
	public NPCAbstraction(String name, Invocable logic) {
		this.name = name;
		this.logic = logic;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the logic of the NPC
	 * 
	 * @return NPC logic
	 */
	protected Invocable getLogic() {
		return logic;
	}
}
