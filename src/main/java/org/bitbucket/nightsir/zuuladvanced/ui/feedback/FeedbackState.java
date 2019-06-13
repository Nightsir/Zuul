package org.bitbucket.nightsir.zuuladvanced.ui.feedback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.Action;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.AdditionalInfoAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.GameIntroAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.ItemInfoAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.PrintAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.RoomContentAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.RoomInfoAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.SpeakAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.UnknownCommandStructureAction;
import org.bitbucket.nightsir.zuuladvanced.ui.feedback.action.WorldMapAction;

/**
 * State class for giving Feedback to the UI form anywhere in the application.
 * 
 * @author Christian Sami
 */
public class FeedbackState {
	public static final int DEFAULT_FEEDBACK = 000;
	public static final int CMD_ERROR = 001;
	public static final int GAME_INTRO = 002;
	public static final int GAME_HELP = 003;
	
	public static final int WINDOW_WORLD_MAP = 020;
	
	public static final int DOOR_NOT_PRESENT = 100;
	public static final int DOOR_LOCKED = 101;
	public static final int DOOR_NOT_LOCKED = 102;
	public static final int DOOR_UNLOCKED = 103;
	public static final int DOOR_NO_KEY = 104;

	public static final int ITEM_NOT_IN_INVENTORY = 200;
	public static final int ITEM_NOT_ENOUGHT_IN_INVENTORY = 201;
	public static final int ITEM_NOT_IN_ROOM = 202;
	public static final int ITEM_NOT_ENOUGHT_IN_ROOM = 203;
	public static final int ITEM_DESCRIPTION = 204;
	public static final int ITEM_TAKE = 205;
	public static final int ITEM_DROP = 206;
	
	public static final int ROOM_INFO = 300;
	public static final int ROOM_CONTENT = 301;

	public static final int NPC_SPEAK = 400;
	public static final int NPC_ACTION_NOT_ALLOWED = 401;
	public static final int NPC_NOT_FOUND = 404;
	
	private static FeedbackState instance;
	
	private Map<Integer, Action> actionMapping;
	private Queue<ActionInfoPair<Integer, String>> feedbackActionQueue;
	
	private FeedbackState() {
		feedbackActionQueue = new LinkedList<>();
		
		actionMapping = new HashMap<>();
		
		actionMapping.put(DEFAULT_FEEDBACK, () -> {});
		actionMapping.put(CMD_ERROR, new UnknownCommandStructureAction());
		actionMapping.put(GAME_INTRO, new GameIntroAction());
		actionMapping.put(GAME_HELP, new PrintAction("txt_help"));
		
		actionMapping.put(WINDOW_WORLD_MAP, new WorldMapAction());
		
		actionMapping.put(DOOR_NOT_PRESENT, new PrintAction("txt_door_nt_pres"));
		actionMapping.put(DOOR_LOCKED, new PrintAction("txt_door_locked"));
		actionMapping.put(DOOR_NOT_LOCKED, new PrintAction("txt_door_no_lock"));
		actionMapping.put(DOOR_UNLOCKED, new PrintAction("txt_door_unlockd"));
		actionMapping.put(DOOR_NO_KEY, new PrintAction("txt_door_no_key"));

		actionMapping.put(ITEM_NOT_IN_INVENTORY, new PrintAction("txt_no_item_inv"));
		actionMapping.put(ITEM_NOT_ENOUGHT_IN_INVENTORY, new PrintAction("txt_lt_item_inv"));
		actionMapping.put(ITEM_NOT_IN_ROOM, new PrintAction("txt_no_item_room"));
		actionMapping.put(ITEM_NOT_ENOUGHT_IN_ROOM, new PrintAction("txt_lt_item_room"));
		actionMapping.put(ITEM_DESCRIPTION, new ItemInfoAction());
		actionMapping.put(ITEM_TAKE, () -> {});
		actionMapping.put(ITEM_DROP, () -> {});
		
		actionMapping.put(ROOM_INFO, new RoomInfoAction());
		actionMapping.put(ROOM_CONTENT, new RoomContentAction());
		
		actionMapping.put(NPC_SPEAK, new SpeakAction());
		actionMapping.put(NPC_ACTION_NOT_ALLOWED, new PrintAction("txt_npc_no_intac"));
		actionMapping.put(NPC_NOT_FOUND, new PrintAction("txt_npc_404"));
	}
	
	/**
	 * Getter for the Singleton instance of {@link FeedbackState}.
	 * 
	 * @return instance of {@link FeedbackState}
	 */
	public static FeedbackState get() {
		if (instance == null) {
			instance = new FeedbackState();
		}
		return instance;
	}
	
	/**
	 * Adds a action to the queue without any additional information.
	 * 
	 * @see #addFeedbackAction(int, String)
	 * 
	 * @param actionKey key of the feedback to invoke
	 */
	public void addFeedbackAction(int actionKey) {
		addFeedbackAction(actionKey, null);
	}
	
	/**
	 * Adds a action to the queue with additional information.
	 * 
	 * @see #addFeedbackAction(int)
	 * 
	 * @param actionKey key of the feedback to invoke
	 * @param additionalInformation given to the action
	 */
	public void addFeedbackAction(int actionKey, String additionalInformation) {
		feedbackActionQueue.offer(new ActionInfoPair<Integer, String>(actionKey, additionalInformation));
	}
	
	/**
	 * Invokes all queued feedback actions in the order they were added.
	 */
	public void invoke() {
		if (feedbackActionQueue.isEmpty()) {
			actionMapping.get(DEFAULT_FEEDBACK).invoke();
			return;
		}
		
		ActionInfoPair<Integer, String> actionInfoPair;
		while ((actionInfoPair = feedbackActionQueue.poll()) != null) {
			Action action = actionMapping.get(actionInfoPair.getAction());
			if (action instanceof AdditionalInfoAction) {
				((AdditionalInfoAction) action).invoke(actionInfoPair.getInfo());
			} else {
				action.invoke();
			}
		}
	}
}