package org.bitbucket.nightsir.zuuladvanced.model.npc;

import org.bitbucket.nightsir.zuuladvanced.model.npc.types.returntypes.TradeItem;

/**
 * Enumeration for action-types that can be invoked on a {@link NonPlayerCharacter}.
 * 
 * @author Christian Sami
 */
public enum NPCActionType {
	SPEAK("speak", String.class),
	TRADE_BUY_GET("getBuyList", TradeItem[].class),
	TRADE_BUY_UPDATE("updateBuyList", Void.class),
	TRADE_BUY_VALUE_MOD("buyValueModifier", Double.class),
	TRADE_SELL_VALUE_MOD("sellValueModifier", Double.class);
	
	private String actionTypeKey;
	private Class<?> returnType;

	private NPCActionType(String actionTypeKey, Class<?> returnType) {
		this.actionTypeKey = actionTypeKey;
		this.returnType = returnType;
	}
	
	/**
	 * Gets the key for the action.<br>
	 * Key corresponds with the function-name called in JS of a NPC.
	 * 
	 * @return key of the action-type
	 */
	public String getActionTypeKey() {
		return actionTypeKey;
	}
	
	/**
	 * Gets the return type that you should get
	 * if you call the JS function named like the corresponding key.
	 * 
	 * @return expected return type of JS function
	 */
	public Class<?> getReturnType() {
		return returnType;
	}
}
