package org.bitbucket.nightsir.zuuladvanced.command.action.npc;

import java.util.Optional;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.NonAllowedActionTypeException;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Abstraction of a NPC player-action which should interact with the user directly.<br>
 * Provides a method to invoke a NPC action and directly return the answer to the user.
 * 
 * @author Christian Sami
 */
public abstract class DirectFeedbackNPCAction implements CommandAction {
	@SuppressWarnings("unchecked")
	private <T> void invokeNpcAction(NPCActionType actionType, String npcName, int feedbackAction, Object playerInteraction, T returnTypeClass) {
		Optional<NonPlayerCharacter> character = GameState.get().getCurrentRoom().getNpcs().getNpcByName(npcName);
		if (character.isPresent()) {
			try {
				T answer = (T) character.get().invokeAction(actionType, playerInteraction);
				FeedbackState.get().addFeedbackAction(feedbackAction, answer.toString());
			} catch (NonAllowedActionTypeException e) {
				FeedbackState.get().addFeedbackAction(FeedbackState.NPC_ACTION_NOT_ALLOWED);
			}
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.NPC_NOT_FOUND);
		}
	}
	
	/**
	 * Invokes the given {@link NPCActionType} on the given NPC.
	 * The Feedback to invoke is set by the given feedbackAction from {@link FeedbackState}.
	 * Information given to the NPC is defined by the playerInteraction.
	 * 
	 * @param actionType the type to invoke
	 * @param npcName the name of the NPC
	 * @param feedbackAction the feedback-key to invoke
	 * @param playerInteraction information given to the NPC
	 */
	protected void invokeNpcAction(NPCActionType actionType, String npcName, int feedbackAction, Object playerInteraction) {
		invokeNpcAction(actionType, npcName, feedbackAction, playerInteraction, actionType.getReturnType());
	}
}