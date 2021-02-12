package at.uibk.dps.ee.core.exception;

import at.uibk.dps.ee.core.enactable.Enactable;

/**
 * The {@link StopException} is thrown by an {@link Enactable} when it stops the
 * execution on its own accord.
 * 
 * @author Fedor Smirnov
 *
 */
public class StopException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Constructed by providing the reason for the stop.
   * 
   * @param stoppingReason the reason for the stop
   */
  public StopException(final String message) {
    super(message);
  }
}
