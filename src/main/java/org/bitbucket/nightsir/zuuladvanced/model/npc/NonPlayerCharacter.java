package org.bitbucket.nightsir.zuuladvanced.model.npc;

import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.AllowedNPCActionTypes;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.NonAllowedActionTypeException;

/**
 * Interface for {@link NonPlayerCharacter}.<br>
 * Defines that all should have a name and
 * a possibility to invoke an Action.
 * 
 * @author Christian Sami
 */
@AllowedNPCActionTypes()
public interface NonPlayerCharacter {
	/**
	 * Invokes a given action-type on the NPC.
	 * Also adds more information to the action with the help of playerInformation and
	 * gives back the information from the NPC.
	 * 
	 * @param type of the action
	 * @param playerInteraction interaction information the player wants to do
	 * @return interaction information form the NPC
	 * @throws NonAllowedActionTypeException if the given action-type is not supported by this NPC-type
	 */
	public Object invokeAction(NPCActionType type, Object playerInteraction) throws NonAllowedActionTypeException;
	
	/**
	 * Gets the name of the NPC.
	 * 
	 * @return NPC name
	 */
	public String getName();
}