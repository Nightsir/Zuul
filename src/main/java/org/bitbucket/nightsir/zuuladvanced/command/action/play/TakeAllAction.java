package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.item.loading.ItemLoader;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;
import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for taking all of an item from the current room.
 * 
 * @author Christian Sami
 */
public class TakeAllAction implements CommandAction {
	@Override
	public void invoke(String param, String caller) {
		if (param.isEmpty()) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
			return;
		}
		
		String item = ItemLoader.get().getCorrectItemName(param);
		int count = GameState.get().removeAllOfItem(item);
		InventoryState.get().addAmountOfItem(item, count);
		
		FeedbackState.get().addFeedbackAction(FeedbackState.ITEM_TAKE);
	}
}
