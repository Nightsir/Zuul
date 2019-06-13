package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;

/**
 * Generic action to print a text predefined by the corresponding configuration key.
 * 
 * @author Christian Sami
 */
public class PrintAction implements Action {
	private String printKey;

	/**
	 * Creates a {@link PrintAction}.
	 * 
	 * @param printKey configuration key of the text to print
	 */
	public PrintAction(String printKey) {
		this.printKey = printKey;
	}

	@Override
	public void invoke() {
		System.out.println(Configuration.get(printKey));
	}
}
