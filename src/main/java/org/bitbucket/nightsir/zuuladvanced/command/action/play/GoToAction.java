package org.bitbucket.nightsir.zuuladvanced.command.action.play;

import org.bitbucket.nightsir.zuuladvanced.command.action.CommandAction;
import org.bitbucket.nightsir.zuuladvanced.model.Direction;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

/**
 * Action to go in a specified direction.
 * 
 * @author Christian Sami
 */
public class GoToAction implements CommandAction {
	private Direction direction;
	
	/**
	 * Constructs an Action for a specific direction movement.
	 * 
	 * @param direction which the action is for
	 */
	public GoToAction(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void invoke(String param, String caller) {
		GameState.get().goToRoom(direction);
	}
}
