package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

/**
 * Abstract class for actions that should get some additional information.
 * 
 * @author Christian Sami
 */
public abstract class AdditionalInfoAction implements Action {
	/**
	 * Invokes the action with some additional information.
	 * 
	 * @param additionalInformation the action should use
	 */
	public abstract void invoke(String additionalInformation);
	
	/**
	 * Invokes the action with an empty string.
	 */
	@Override
	public void invoke() {
		invoke("");
	}
}
