package org.bitbucket.nightsir.zuuladvanced.command.action.npc;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for speaking with a NPC.
 * 
 * @author Christian Sami
 */
public class SpeakAction extends DirectFeedbackNPCAction {
	@Override
	public void invoke(String param, String caller) {
		if (!param.isEmpty()) {
			String[] paramParts = param.split(":", 2);
			String npcName = paramParts[0].trim();
			
			invokeNpcAction(NPCActionType.SPEAK, npcName, FeedbackState.NPC_SPEAK, paramParts.length == 2 ? paramParts[1].trim() : "");
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
		}
	}
}
