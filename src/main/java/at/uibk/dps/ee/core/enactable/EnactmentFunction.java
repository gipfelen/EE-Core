package at.uibk.dps.ee.core.enactable;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;

/**
 * The {@link EnactmentFunction} models the concrete behavior of an {@link Enactable}. Assigning an
 * {@link EnactmentFunction} to an {@link Enactable} corresponds to the scheduling step.
 * 
 * @author Fedor Smirnov
 */
public interface EnactmentFunction {

  /**
   * Processes the input json object and returns the result of the function.
   * 
   * @param input
   * @return the json object generated as the result of the process
   * @throws StopException exception thrown if errors occur during the processing
   */
  JsonObject processInput(JsonObject input) throws StopException;
}
