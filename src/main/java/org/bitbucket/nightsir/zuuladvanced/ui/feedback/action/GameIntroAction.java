package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;

/**
 * Action for printing the intro of the game.
 * 
 * @author Christian Sami
 */
public class GameIntroAction implements Action {
	@Override
	public void invoke() {
		System.out.println(Configuration.get("txt_intro"));
	}
}
