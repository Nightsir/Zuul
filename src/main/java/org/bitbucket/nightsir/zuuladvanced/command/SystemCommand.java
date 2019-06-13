package org.bitbucket.nightsir.zuuladvanced.command;

import org.bitbucket.nightsir.zuuladvanced.ui.controller.WindowController;

/**
 * Class for commands not thought to be executed by the player directly.<br>
 * This class contains methods for changing from and to a developer/debug mode,
 * checking if it is in developer/debug mode and starting a monitoring window.
 * 
 * @author Christian Sami
 */
public class SystemCommand {
	private static boolean developerMode;
	
	static {
		developerMode = false;
	}
	
	/**
	 * Checks if the developer/debug mode is active.
	 * 
	 * @return if developer/debug mode is active
	 */
	public static boolean isDeveloperModeActiv() {
		return developerMode;
	}
	
	/**
	 * Changes from and to developer/debug mode
	 * and gives back if it is now active or not.
	 * 
	 * @return new state of developer/debug mode.
	 */
	public static boolean changeDeveloperMode() {
		developerMode = !developerMode;
		return developerMode;
	}
	
	/**
	 * Starts the monitoring window if
	 * the developer/debug mode is active at the moment.
	 * 
	 * @return if the window was opened or not
	 */
	public static boolean startMonitoring() {
		if (developerMode) {
			WindowController.get().openMonitoringWindow();
		}
		return developerMode;
	}
}
