package org.bitbucket.nightsir.zuuladvanced.command.action;

import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for generic feedback specified by a feedback-key to the player.
 * 
 * @author Christian Sami
 */
public class FeedbackAction implements CommandAction {
	private int feedbackKey;
	
	/**
	 * Constructs an Action for a feedback specified by a feedback-key.
	 * 
	 * @param feedbackKey the key for specifying the called feedback
	 */
	public FeedbackAction(int feedbackKey) {
		this.feedbackKey = feedbackKey;
	}

	@Override
	public void invoke(String param, String caller) {
		FeedbackState.get().addFeedbackAction(feedbackKey, param);
	}
}
