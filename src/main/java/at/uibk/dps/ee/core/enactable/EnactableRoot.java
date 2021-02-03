package at.uibk.dps.ee.core.enactable;

import java.util.Set;

import com.google.gson.JsonObject;

/**
 * The {@link EnactableRoot} is the enactable modeling the enactment of the
 * overall Workflow.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class EnactableRoot extends Enactable {

	protected JsonObject wfInput;

	/**
	 * Standard constructor.
	 * 
	 * @param stateListeners
	 */
	protected EnactableRoot(final Set<EnactableStateListener> stateListeners) {
		super(stateListeners);
	}

	/**
	 * Sets the json object which is to be used as WF input.
	 * 
	 * @param wfInput the json object which is to be used as WF input
	 */
	public void setInput(final JsonObject wfInput) {
		this.wfInput = wfInput;
	}
}
