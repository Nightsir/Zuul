package org.bitbucket.nightsir.zuuladvanced.model.npc.types;

import javax.script.Invocable;
import javax.script.ScriptException;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.AllowedNPCActionTypes;
import org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception.NonAllowedActionTypeException;
import org.bitbucket.nightsir.zuuladvanced.model.npc.proxytyping.AbstractTrader;

/**
 * Implementation of a trading NPC.
 * 
 * @author Christian Sami
 */
@AllowedNPCActionTypes({ NPCActionType.SPEAK,
		NPCActionType.TRADE_BUY_GET, NPCActionType.TRADE_BUY_UPDATE,
			NPCActionType.TRADE_BUY_VALUE_MOD, NPCActionType.TRADE_SELL_VALUE_MOD })
public class Trader extends NPCAbstraction implements AbstractTrader {
	private boolean reseller;
	
	/**
	 * Normal constructor for a trader.
	 * 
	 * @see #Trader(String, Invocable, String)
	 * 
	 * @param name of the trader
	 * @param logic of the trader
	 * @param reseller if the trader resells items
	 */
	public Trader(String name, Invocable logic, boolean reseller) {
		super(name, logic);
		this.reseller = reseller;
	}
	
	/**
	 * Factory constructor for a trader.
	 * 
	 * @see #Trader(String, Invocable, boolean)
	 * @see Boolean#parseBoolean(String)
	 * 
	 * @param name of the trader
	 * @param logic of the trader
	 * @param reseller if the trader resells items
	 */
	public Trader(String name, Invocable logic, String reseller) {
		this(name, logic, Boolean.parseBoolean(reseller));
	}

	@Override
	public Object invokeAction(NPCActionType type, Object playerInteraction) throws NonAllowedActionTypeException {
		try {
			Object answer = null;
			switch (type) {
				case TRADE_BUY_UPDATE:
					break;
				case TRADE_SELL_VALUE_MOD:
					String[] itemSellInformation = ((String) playerInteraction).split(";", 2);
					answer = type.getReturnType().cast(getLogic().invokeFunction(type.getActionTypeKey(), itemSellInformation[0], itemSellInformation[1]));
					break;
				case TRADE_BUY_GET:
				case SPEAK:
				default:
					answer = getLogic().invokeFunction(type.getActionTypeKey(), playerInteraction);
					break;
			}
			return answer;
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean isReseller() {
		return reseller;
	}
}