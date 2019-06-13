package org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;

/**
 * Exception to indicate that a {@link NPCActionType} was invoked on a {@link NonPlayerCharacter}
 * which was not allowed.
 * 
 * @author Christian Sami
 */
public class NonAllowedActionTypeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a exception with the key of the given {@link NPCActionType} as Message.
	 * 
	 * @param actionType that was not allowed
	 */
	public NonAllowedActionTypeException(NPCActionType actionType) {
		super(actionType.getActionTypeKey());
	}
	
	/**
	 * Creates a exception with the key of the given {@link NPCActionType} and
	 * the additionalInformation as Message.
	 * 
	 * @param actionType that was not allowed
	 * @param additionalInformation wanted to be add to the exception
	 */
	public NonAllowedActionTypeException(NPCActionType actionType, String additionalInformation) {
		super(actionType.getActionTypeKey() + ": " + additionalInformation);
	}
}
