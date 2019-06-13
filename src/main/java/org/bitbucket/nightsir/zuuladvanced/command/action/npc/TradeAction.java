package org.bitbucket.nightsir.zuuladvanced.command.action.npc;

import java.util.Optional;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;
import org.bitbucket.nightsir.zuuladvanced.model.npc.proxytyping.AbstractTrader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.ui.controller.WindowController;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for trading with a NPC.
 * 
 * @author Christian Sami
 */
public class TradeAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		String[] paramParts = param.split(":", 2);
		String npcName = paramParts[0].trim();
		Optional<NonPlayerCharacter> character = GameState.get().getCurrentRoom().getNpcs().getNpcByName(npcName);
		if (character.isPresent()) {
			if (character.get() instanceof AbstractTrader) {
				WindowController.get().openTradeWindow((AbstractTrader) character.get());
			} else {
				FeedbackState.get().addFeedbackAction(FeedbackState.NPC_ACTION_NOT_ALLOWED);
			}
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.NPC_NOT_FOUND);
		}
	}
}
