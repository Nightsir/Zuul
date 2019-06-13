package org.bitbucket.nightsir.zuuladvanced.command.action;

import java.util.Arrays;
import java.util.Optional;

import org.bitbucket.nightsir.zuuladvanced.command.Command;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Action for a command which is split in multiple key words / command parts
 * on which there is a possibility for a command with an amount of something.
 * 
 * @author Christian Sami
 */
public class MultiCommandWithNumericAction implements CommandAction {
	private Command numericCommand;
	private Command[] commands;
	
	/**
	 * Constructs an Action for split commands with the possibility for amount of something.<br>
	 * The following commands are specified by the given commands.
	 * The numeric command should be called with 1 if on the input no number is present.
	 * 
	 * @param numericCommand following numeric command
	 * @param commands following non numeric command
	 */
	public MultiCommandWithNumericAction(Command numericCommand, Command... commands) {
		this.numericCommand = numericCommand;
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
			numericCommand.execute(paramParts.length < 2 ? "1 " + param : param, caller);
		}
	}
}
