package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

/**
 * Action to unlock a room in a specified direction.
 * 
 * @author Christian Sami
 */
public class UnlockAction implements CommandAction {
	private Direction direction;
	
	/**
	 * Constructs an Action to unlock a door in a specific direction.
	 * 
	 * @param direction which the action is for
	 */
	public UnlockAction(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void invoke(String param, String caller) {
		GameState.get().unlockRoom(direction);
	}
}
