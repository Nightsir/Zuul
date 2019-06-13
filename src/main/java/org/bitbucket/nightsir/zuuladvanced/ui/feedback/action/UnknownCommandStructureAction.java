package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import java.util.HashMap;
import java.util.Map;

import org.bitbucket.nightsir.zuuladvanced.command.Command;
import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;

/**
 * Action for error messages if a {@link Command} was used incorrectly or the command is unknown.
 * 
 * @author Christian Sami
 */
public class UnknownCommandStructureAction extends AdditionalInfoAction {
	private static final Map<String, String> configurationErrorKey = new HashMap<>();
	
	static {
		configurationErrorKey.put(Command.GO.getCommandString(), "err_ukn_go");
		configurationErrorKey.put(Command.UNLOCK.getCommandString(), "err_ukn_unlock");
		configurationErrorKey.put(Command.OPEN.getCommandString(), "err_ukn_open");
		configurationErrorKey.put(Command.LOOK.getCommandString(), "err_ukn_look");
		configurationErrorKey.put(Command.TAKE.getCommandString(), "err_ukn_take");
		configurationErrorKey.put(Command.DROP.getCommandString(), "err_ukn_drop");
		configurationErrorKey.put(Command.TALK.getCommandString(), "err_ukn_talk");
		configurationErrorKey.put(Command.TRADE.getCommandString(), "err_ukn_trade");
	}
	
	@Override
	public void invoke(String param) {
		String key;
		String additional = "";
		if (configurationErrorKey.containsKey(param)) {
			key = configurationErrorKey.get(param);
		} else {
			key = "err_ukn_cmd";
			additional = param;
		}
		System.out.println(Configuration.get(key) + additional);
	}
}
