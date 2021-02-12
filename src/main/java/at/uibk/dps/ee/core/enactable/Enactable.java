package at.uibk.dps.ee.core.enactable;

import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;

/**
 * Interface for application components which can be enacted.
 * 
 * @author Fedor Smirnov
 */
public class Enactable {

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
     * Input data present. Ready to be scheduled.
     */
    SCHEDULABLE,
    /**
     * Scheduled. Can be launched
     */
    LAUNCHABLE,
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

  protected JsonObject jsonInput;
  protected JsonObject jsonResult;
  protected EnactmentFunction enactmentFunction;

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
   * Returns the {@link JsonObject} with the result of the enactment.
   * 
   * @return the enactment result.
   */
  public JsonObject getResult() {
    if (!getState().equals(State.FINISHED)) {
      throw new IllegalStateException("Result requested before enactment finished.");
    }
    return Optional.ofNullable(jsonResult).orElseThrow(
        () -> new IllegalStateException("Result requested before enactment finished."));
  }

  /**
   * Sets the given value of the input {@link JsonObject}.
   * 
   * @param key the key string
   * @param value the value which is set
   */
  public void setInputValue(final String key, final JsonElement value) {
    jsonInput = Optional.ofNullable(jsonInput).orElseGet(() -> new JsonObject());
    jsonInput.add(key, value);
  }

  /**
   * Returns the input which is set for the enactable.
   * 
   * @return the input which is set for the enactable
   */
  public JsonObject getInput() {
    return Optional.ofNullable(jsonInput)
        .orElseThrow(() -> new IllegalStateException("Input requested before it is set."));
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
  public void play() throws StopException {
    if (!getState().equals(State.LAUNCHABLE)) {
      throw new StopException("The enactable cannot be played since it is not yet launchable.");
    }
    setState(State.RUNNING);
    try {
      jsonResult = enactmentFunction.processInput(jsonInput);
      setState(State.FINISHED);
    } catch (StopException stopExc) {
      setState(State.STOPPED);
      throw stopExc;
    }
  }

  /**
   * Assigns the given function to the enactable.
   * 
   * @param function the given function.
   */
  public void schedule(final EnactmentFunction function) {
    if (!getState().equals(State.SCHEDULABLE)) {
      throw new IllegalStateException("The enactable is not yet schedulable.");
    }
    this.enactmentFunction = function;
    this.setState(State.LAUNCHABLE);
  }

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
  protected void myPause() {
    // Nothing; can be overwritten by children.
  }

  /**
   * Resets the state of the enactable so that it can be enacted again after a
   * previous enactament or an error.
   * 
   */
  public final void reset() {
    myReset();
    setState(State.WAITING);
  }

  /**
   * Method to define class-specific reset behavior.
   */
  protected void myReset() {
    // Nothing; can be overwritten by children.
  }

  /**
   * Sets the object into its initial state. Can also be used to reset the state.
   * 
   * @param input the data required for the execution
   */
  public final void init() {
    myInit();
    setState(State.SCHEDULABLE);
  }

  /**
   * Method to provide the class-specific init details.
   * 
   * @param inputData the input data.
   */
  protected void myInit() {
    // Nothing; can be overwritten by children.
  }

  /**
   * Returns the state of the enactable.
   * 
   * @return the state of the enactable.
   */
  public State getState() {
    State result;
    synchronized (this) {
      result = this.state;
    }
    return result;
  }

  /**
   * Sets the State of the enactable and notifies all state listeners.
   * 
   * @param state the new state
   */
  public void setState(final State state) {
    State previous;
    State current;
    synchronized (this) {
      previous = this.state;
      current = state;
      this.state = state;
    }
    for (final EnactableStateListener stateListener : stateListeners) {
      stateListener.enactableStateChanged(this, previous, current);
    }
  }

  @Override
  public String toString() {
    final String inString = Optional.ofNullable(jsonInput).orElse(new JsonObject()).toString();
    final String outString = Optional.ofNullable(jsonResult).orElse(new JsonObject()).toString();
    return "in: " + inString + " out: " + outString;
  }
}
