package at.uibk.dps.ee.core.enactable;

import java.util.Set;

import at.uibk.dps.ee.core.exception.StopException;

/**
 * Interface for application components which can be enacted.
 * 
 * @author Fedor Smirnov
 */
public abstract class Enactable{

	/**
	 * The enactment state of the enactable
	 * 
	 * @author Fedor Smirnov
	 *
	 */
	public enum State {
		/**
		 * Waiting for the provision of input data. Not enactable.
		 */
		WAITING,
		/**
		 * Input data present. Ready to start the enactment.
		 */
		READY,
		/**
		 * Running the underlying functionality.
		 */
		RUNNING,
		/**
		 * Paused by the enactment control.
		 */
		PAUSED,
		/**
		 * Stopped due to an internal Stop Exception
		 */
		STOPPED,
		/**
		 * Enactment finished, output data retrieved.
		 */
		FINISHED
	}

	protected State state = State.WAITING;

	protected final Set<EnactableStateListener> stateListeners;

	/**
	 * It is recommended to build enactables using a factory to provide them with
	 * the required listeners. Consequently, the constructor is protected.
	 * 
	 * @param stateListeners the list of listeners to notify of state changes
	 */
	protected Enactable(final Set<EnactableStateListener> stateListeners) {
		this.stateListeners = stateListeners;
	}

	/**
	 * Triggers the execution from the current state of the enactable. This results
	 * in an execution and a change of states until (a) the execution is finished
	 * and the enactable returns the output data, (b) the execution is paused from
	 * outside, or (c) the execution is stopped due to an internal condition,
	 * throwing a {@link StopException}.
	 * 
	 * @return the output data
	 */
	public final void play() throws StopException {
		if (!getState().equals(State.READY)) {
			throw new IllegalStateException("The enactable cannot be played since it is not in the READY state.");
		}
		setState(State.RUNNING);
		try {
			myPlay();
			setState(State.FINISHED);
		} catch (StopException stopExc) {
			setState(State.STOPPED);
			throw stopExc;
		}
	}
	
	

	/**
	 * Method to define the class-specific play behavior.
	 * 
	 * @return the output data
	 * @throws StopException a stop exception.
	 */
	protected abstract void myPlay() throws StopException;

	/**
	 * Pauses the execution of the enactable by stopping the execution while
	 * preserving the inner state of the enactable.
	 */
	public final void pause() {
		myPause();
		setState(State.PAUSED);
	}

	/**
	 * Method for the class-specific reaction to a pause request.
	 * 
	 */
	protected abstract void myPause();

	/**
	 * Sets the object into its initial state. Can also be used to reset the state.
	 * 
	 * @param input the data required for the execution
	 */
	public final void init() {
		myInit();
		setState(State.READY);
	}

	/**
	 * Method to provide the class-specific init details.
	 * 
	 * @param inputData the input data.
	 */
	protected abstract void myInit();

	public State getState() {
		return state;
	}

	/**
	 * Sets the State of the enactable and notifies all state listeners.
	 * 
	 * @param state the new state
	 */
	public void setState(final State state) {
		final State previous = this.state;
		final State current = state;
		for (final EnactableStateListener stateListener : stateListeners) {
			stateListener.enactableStateChanged(this, previous, current);
		}
		this.state = state;
	}
}
