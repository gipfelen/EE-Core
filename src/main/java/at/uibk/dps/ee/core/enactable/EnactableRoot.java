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
	protected EnactableRoot(Set<EnactableStateListener> stateListeners) {
		super(stateListeners);
	}

	/**
	 * Sets the json object which is to be used as WF input.
	 * 
	 * @param wfInput the json object which is to be used as WF input
	 */
	public void setInput(JsonObject wfInput) {
		this.wfInput = wfInput;
	}

	/**
	 * Returns the JsonObject generated as the result of the WF enactment.
	 * 
	 * @return the JsonObject generated as the result of the WF enactment
	 */
	public abstract JsonObject getOutput();
}
