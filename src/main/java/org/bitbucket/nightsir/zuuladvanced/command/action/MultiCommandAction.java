package org.bitbucket.nightsir.zuuladvanced.command.action;

import java.util.Arrays;
import java.util.Optional;

import org.bitbucket.nightsir.zuuladvanced.command.Command;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for a command which is split in multiple key words / command parts.
 * 
 * @author Christian Sami
 */
public class MultiCommandAction implements CommandAction {
	private Command[] commands;
	
	/**
	 * Constructs an Action for split commands.<br>
	 * The following commands are specified by the given commands.
	 * 
	 * @param commands following commands
	 */
	public MultiCommandAction(Command... commands) {
		this.commands = commands;
	}

	@Override
	public void invoke(String param, String caller) {
		if (param.isEmpty()) {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
			return;
		}
		
		String[] paramParts = param.split(" ", 2);
		Optional<Command> command = Arrays.asList(commands).stream().filter(
				(c) -> c.equals(paramParts[0])).findAny();
		
		if (command.isPresent()) {
			command.get().execute(paramParts.length < 2 ? "" : paramParts[1], caller);
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, caller);
		}
	}
}
