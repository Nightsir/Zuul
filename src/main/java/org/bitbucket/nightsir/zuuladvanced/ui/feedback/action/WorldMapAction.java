package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.ui.controller.WindowController;

/**
 * Action for opening the world map.
 * 
 * @author Christian Sami
 */
public class WorldMapAction implements Action {
	@Override
	public void invoke() {
		WindowController.get().openWorldMapWindow();
	}
}
