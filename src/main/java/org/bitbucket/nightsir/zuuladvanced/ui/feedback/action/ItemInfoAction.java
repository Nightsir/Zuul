package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.state.InventoryState;

/**
 * Action to print the description of a given item.
 * 
 * @author Christian Sami
 */
public class ItemInfoAction extends AdditionalInfoAction {
	@Override
	public void invoke(String additionalInformation) {
		System.out.println(InventoryState.get().getItemDescriptionIfInInventory(additionalInformation).get());
	}
}