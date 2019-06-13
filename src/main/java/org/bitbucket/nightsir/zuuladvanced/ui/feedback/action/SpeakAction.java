package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;

/**
 * Action to speak to a NPC.
 * 
 * @author Christian Sami
 */
public class SpeakAction extends AdditionalInfoAction {
	@Override
	public void invoke(String additionalInformation) {
		if (additionalInformation.isEmpty()) {
			System.out.println(Configuration.get("txt_npc_no_answ"));
		} else {
			System.out.println(additionalInformation);
		}
	}
}
