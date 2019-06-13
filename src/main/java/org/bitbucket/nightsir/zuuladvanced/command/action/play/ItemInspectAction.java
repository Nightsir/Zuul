package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for inspection an item in the player inventory.
 * 
 * @author Christian Sami
 */
public class ItemInspectAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		if (param.isEmpty()) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
			return;
		}
		
		String item = ItemLoader.get().getCorrectItemName(param);
		if (InventoryState.get().isItemInInventory(item)) {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_DESCRIPTION, item);
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_NOT_IN_INVENTORY);
		}
	}
}
