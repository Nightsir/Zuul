package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for taking a specific amount of an item from the current room.
 * 
 * @author Christian Sami
 */
public class TakeAmmountAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		String[] parts = param.split(" ", 2);
		try {
			String item = ItemLoader.get().getCorrectItemName(parts[1]);
			int count = GameState.get().removeAmountOfItem(item, Integer.parseInt(parts[0]));
			InventoryState.get().addAmountOfItem(item, count);
			
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_TAKE);
		} catch (NumberFormatException nfe) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
		}
	}
}
