package at.uibk.dps.ee.core.enactable;

import at.uibk.dps.ee.core.enactable.Enactable.State;

/**
 * Interface for all classes which need to react to a change in the state of an
 * {@link Enactable}.
 * 
 * @author Fedor Smirnov
 *
 */
public interface EnactableStateListener {

	/**
	 * React to a change of the state of the given enactable from previous to
	 * current state.
	 * 
	 * @param enactable     the given enactable
	 * @param previousState the previous state
	 * @param currentState  the current state
	 */
	void enactableStateChanged(Enactable enactable, State previousState, State currentState);

}
