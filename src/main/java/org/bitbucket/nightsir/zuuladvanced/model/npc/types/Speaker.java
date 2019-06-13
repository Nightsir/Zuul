package org.bitbucket.nightsir.zuuladvanced.model.npc.types;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.AllowedNPCActionTypes;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.NonAllowedActionTypeException;

/**
 * Implementation of a speaking NPC.
 * 
 * @author Christian Sami
 */
@AllowedNPCActionTypes({ NPCActionType.SPEAK })
public class Speaker extends NPCAbstraction {
	/**
	 * Constructs a speaker.
	 * 
	 * @param name of the speaker
	 * @param logic of the speaker
	 */
	public Speaker(String name, Invocable logic) {
		super(name, logic);
	}

	@Override
	public Object invokeAction(NPCActionType type, Object playerInteraction) throws NonAllowedActionTypeException {
		try {
			return getLogic().invokeFunction(type.getActionTypeKey(), playerInteraction);
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}
}
