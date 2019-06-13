package org.bitbucket.nightsir.zuuladvanced.command.action;

/**
 * Action for any player command.
 * 
 * @author Christian Sami
 */
public interface CommandAction {
	/**
	 * Invokes the Action of the command.
	 * 
	 * @param param parameter for the action
	 * @param caller key of the caller
	 */
	public void invoke(String param, String caller);
}