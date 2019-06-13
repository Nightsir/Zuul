package org.bitbucket.nightsir.zuuladvanced.ui.layoutfix;

import javafx.stage.WindowEvent;

/**
 * Interface for Controllers that need an onExit method.
 * 
 * @author Christian Sami
 */
public interface CloseableController {
	/**
	 * Method that should be called when the Controller/UI is closed.
	 * 
	 * @param event
	 */
	public void onExit(WindowEvent event);
}
