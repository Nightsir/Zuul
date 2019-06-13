package org.bitbucket.nightsir.zuuladvanced.ui.feedback;

/**
 * Class for a pairing of an action and some information.
 * 
 * @author Christian Sami
 * @param <A> class of the action
 * @param <I> class of the information
 */
public class ActionInfoPair<A, I> {
	private A action;
	private I info;

	/**
	 * Creates a {@link ActionInfoPair}
	 * 
	 * @param action of the pair
	 * @param info of the pair
	 */
	public ActionInfoPair(A action, I info) {
		this.action = action;
		this.info = info;
	}

	/**
	 * Gets the action.
	 * 
	 * @return action
	 */
	public A getAction() {
		return action;
	}

	/**
	 * Gets the information.
	 * 
	 * @return information
	 */
	public I getInfo() {
		return info;
	};
}
