package org.bitbucket.nightsir.zuuladvanced.command;

import java.util.Arrays;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.FeedbackAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.MultiCommandAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.MultiCommandWithNumericAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.npc.SpeakAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.npc.TradeAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.DropAllAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.DropAmmountAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.GoToAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.ItemInspectAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.TakeAllAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.TakeAmmountAction;
import org.bitbucket.nightsir.zuuladvanced.command.action.play.UnlockAction;
import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.FeedbackState;

/**
 * Enumeration for Commands the player can execute.<br>
 * A Command has the information on which player input it should be executed,
 * if it is a primary or a later keyword and which {@link CommandAction} should be called.
 * 
 * @author Christian Sami
 */
public enum Command {
	GO_NORTH(Configuration.get("cmd_north"), false, new GoToAction(Direction.NORTH)),
	GO_EAST(Configuration.get("cmd_east"), false, new GoToAction(Direction.EAST)),
	GO_SOUTH(Configuration.get("cmd_south"), false, new GoToAction(Direction.SOUTH)),
	GO_WEST(Configuration.get("cmd_west"), false, new GoToAction(Direction.WEST)),
	GO(Configuration.get("cmd_go"), true, new MultiCommandAction(GO_NORTH, GO_EAST, GO_SOUTH, GO_WEST)),
	UNLOCK_NORTH(Configuration.get("cmd_north"), false, new UnlockAction(Direction.NORTH)),
	UNLOCK_EAST(Configuration.get("cmd_east"), false, new UnlockAction(Direction.EAST)),
	UNLOCK_SOUTH(Configuration.get("cmd_south"), false, new UnlockAction(Direction.SOUTH)),
	UNLOCK_WEST(Configuration.get("cmd_west"), false, new UnlockAction(Direction.WEST)),
	UNLOCK(Configuration.get("cmd_unlock"), true, new MultiCommandAction(UNLOCK_NORTH, UNLOCK_EAST, UNLOCK_SOUTH, UNLOCK_WEST)),
	OPEN_MAP(Configuration.get("cmd_map"), false, new FeedbackAction(FeedbackState.WINDOW_WORLD_MAP)),
	OPEN(Configuration.get("cmd_open"), true, new MultiCommandAction(OPEN_MAP)),
	LOOK_AROUND(Configuration.get("cmd_around"), false, new FeedbackAction(FeedbackState.ROOM_CONTENT)),
	LOOK_AT(Configuration.get("cmd_at"), false, new ItemInspectAction()),
	LOOK(Configuration.get("cmd_look"), true, new MultiCommandAction(LOOK_AT, LOOK_AROUND)),
	TAKE_ALL(Configuration.get("cmd_all"), false, new TakeAllAction()),
	TAKE_AMOUNT("", false, new TakeAmmountAction()),
	TAKE(Configuration.get("cmd_take"), true, new MultiCommandWithNumericAction(TAKE_AMOUNT, TAKE_ALL)),
	DROP_ALL(Configuration.get("cmd_all"), false, new DropAllAction()),
	DROP_AMOUNT("", false, new DropAmmountAction()),
	DROP(Configuration.get("cmd_drop"), true, new MultiCommandWithNumericAction(DROP_AMOUNT, DROP_ALL)),
	TALK_TO(Configuration.get("cmd_to"), false, new SpeakAction()),
	TALK(Configuration.get("cmd_talk"), true, new MultiCommandAction(TALK_TO)),
	TRADE_WITH(Configuration.get("cmd_with"), false, new TradeAction()),
	TRADE(Configuration.get("cmd_trade"), true, new MultiCommandAction(TRADE_WITH)),
	HELP(Configuration.get("cmd_help"), true, new FeedbackAction(FeedbackState.GAME_HELP)),
	QUIT(Configuration.get("cmd_quit"), true, (a, b) -> System.exit(0));

	private String commandString;
	private boolean firstCommand;
	private CommandAction commandAction;
	
	private Command(String commandString, boolean firstCommand, CommandAction commandAction) {
		this.commandString = commandString;
		this.firstCommand = firstCommand;
		this.commandAction = commandAction;
	}
	
	private static boolean isExistingFirstCommand(String commandString) {
		return Arrays.stream(values()).filter(Command::isFirstCommand).anyMatch(
				(c) -> commandString.startsWith(c.getCommandString()));
	}
	
	private static void executeCommand(String command) {
		Arrays.stream(values()).filter(
				(c) -> !c.getCommandString().isEmpty() && command.startsWith(c.getCommandString()))
			.findAny().ifPresent(
				(c) -> executeCommand(c, command));
	}
	
	private static void executeCommand(Command command, String commandString) {
		String[] commandParts = commandString.split(" ", 2);
		command.execute(commandParts.length < 2 ? "" : commandParts[1]);
	}
	
	/**
	 * Executes the {@link Command} which corresponds to the given String command
	 * if it is a primary command.
	 * 
	 * @param command the String representation of the {@link Command}
	 */
	public static void executeCommandIfExisting(String command) {
		if (isExistingFirstCommand(command)) {
			executeCommand(command);
		} else {
			FeedbackState.get().addFeedbackAction(FeedbackState.CMD_ERROR, command);
		}
	}
	
	/**
	 * Returns the String representation of this.
	 * 
	 * @return String representation
	 */
	public String getCommandString() {
		return commandString;
	}
	
	private boolean isFirstCommand() {
		return firstCommand;
	}
	
	/**
	 * Executes the this with the given parameter
	 * and this as caller.
	 * 
	 * @param param parameter for the executing command
	 */
	public void execute(String param) {
		execute(param, this.commandString);
	}
	
	/**
	 * Executes the this with the given parameter and caller.
	 * 
	 * @param param parameter for the executing command
	 * @param caller String representation of the caller
	 */
	public void execute(String param, String caller) {
		commandAction.invoke(param, caller);
	}
	
	/**
	 * Checks if the given String representation is equals to the String representation of this.
	 * 
	 * @param commandString String representation to check
	 * @return commandString equals String representation of this
	 */
	public boolean equals(String commandString) {
		return this.commandString.equals(commandString);
	}
}
