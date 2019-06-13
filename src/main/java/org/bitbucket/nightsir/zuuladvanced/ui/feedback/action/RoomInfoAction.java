package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.room.Room;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

/**
 * Action to print the main information about a room.
 * 
 * @author Christian Sami
 */
public class RoomInfoAction implements Action {
	@Override
	public void invoke() {
		Room currentRoom = GameState.get().getCurrentRoom();
		System.out.println(Configuration.get("txt_player_pos") + currentRoom.getDescription());
		System.out.println(
				Configuration.get("txt_exits") + 
				currentRoom.getNeighbourPositions()
						.map((d) -> Configuration.get(d.getConfigurationKey()))
						.reduce((a, b) -> a + ", " + b)
						.get());
	}
}
