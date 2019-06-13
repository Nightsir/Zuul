package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for dropping a specific amount of an item in the player inventory.
 * 
 * @author Christian Sami
 */
public class DropAmmountAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		String[] parts = param.split(" ", 2);
		try {
			String item = ItemLoader.get().getCorrectItemName(parts[1]);
			int count = InventoryState.get().removeAmountOfItem(item, Integer.parseInt(parts[0]));
			GameState.get().getCurrentRoom().getItems().addItem(item, count);
			
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_DROP);
		} catch (NumberFormatException nfe) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
		}
	}
}
