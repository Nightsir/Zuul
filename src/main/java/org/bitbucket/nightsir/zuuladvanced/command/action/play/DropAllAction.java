package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for dropping all of an item in the player inventory.
 * 
 * @author Christian Sami
 */
public class DropAllAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		if (param.isEmpty()) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
			return;
		}
		
		String item = ItemLoader.get().getCorrectItemName(param);
		int count = InventoryState.get().removeAllOfItem(item);
		GameState.get().getCurrentRoom().getItems().addItem(item, count);
		
		FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_DROP);
	}
}
