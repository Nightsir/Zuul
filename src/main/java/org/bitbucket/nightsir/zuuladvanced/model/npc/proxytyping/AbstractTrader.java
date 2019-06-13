package org.bitbucket.nightsir.zuuladvanced.model.npc.proxytyping;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;

/**
 * Interface for {@link NonPlayerCharacter} that should be able to trade.
 * 
 * @author Christian Sami
 */
public interface AbstractTrader extends NonPlayerCharacter {
	/**
	 * Checks if this trader should resell item sold by the player.
	 * 
	 * @return if resell of items is possible
	 */
	public boolean isReseller();
}
