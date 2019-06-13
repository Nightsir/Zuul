package org.bitbucket.nightsir.zuuladvanced.ui.feedback.action;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;
import org.bitbucket.nightsir.zuuladvanced.model.room.Room;
import org.bitbucket.nightsir.zuuladvanced.state.GameState;

/**
 * Action to print everything that is in a room.
 * 
 * @author Christian Sami
 */
public class RoomContentAction implements Action {
	@Override
	public void invoke() {
		Room currentRoom = GameState.get().getCurrentRoom();
		boolean completeEmpty = true;
		
		if (!currentRoom.getItems().isEmpty()) {
			String items = currentRoom.getItems().getItemNameWithCount()
					.map((e) -> e.getValue() + " " + e.getKey())
					.reduce((a,b) -> a + ", " + b).get();
			System.out.println(Configuration.get("txt_room_item") + items);
			completeEmpty = false;
		}
		if (!currentRoom.getNpcs().isEmpty()) {
			String npcs = currentRoom.getNpcs().getNpcStream().map(NonPlayerCharacter::getName)
					.reduce((a,b) -> a + ", " + b).get();
			System.out.println(Configuration.get("txt_room_npc") + npcs);
			completeEmpty = false;
		}
		
		if (completeEmpty) {
			System.out.println(Configuration.get("txt_room_empty"));
		}
	}
}
