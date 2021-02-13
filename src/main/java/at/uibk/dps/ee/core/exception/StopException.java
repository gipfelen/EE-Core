package at.uibk.dps.ee.core.exception;

import java.util.Optional;
import at.uibk.dps.ee.core.enactable.Enactable;

/**
 * The {@link StopException} is thrown by an {@link Enactable} when it stops the
 * execution on its own accord.
 * 
 * @author Fedor Smirnov
 *
 */
public class StopException extends Exception {

  private static final long serialVersionUID = 1L;

  protected String stopMessage;
  protected Optional<Exception> initialException = Optional.empty();

  /**
   * Constructed by providing the reason for the stop.
   * 
   * @param stoppingReason the reason for the stop
   */
  public StopException(final String message) {
    this.stopMessage = message;
  }

  /**
   * Constructor where the initial exception shall be stored in the
   * {@link StopException}.
   * 
   * @param message the infor message
   * @param exception the initial exception
   */
  public StopException(final String message, final Exception exception) {
    this.stopMessage = message;
    this.initialException = Optional.of(exception);
  }

  /**
   * Returns true if this stopexception contains an initial exception.
   * 
   * @return true if this stopexception contains an initial exception
   */
  public boolean hasInitialException() {
    return initialException.isPresent();
  }

  /**
   * Appends additional information onto the current stop message.
   * 
   * @param additionalInformation the additional information.
   */
  public void appendExceptionInformation(final String additionalInformation) {
    stopMessage += "\n";
    stopMessage += additionalInformation;
  }

  public String getStopMessage() {
    return stopMessage;
  }

  /**
   * Returns the initial exception--should be used AFTER checking that there
   * actually is an initial exception by calling hasInitialException().
   * 
   * @return the initial exception.
   */
  public Exception getInitialException() {
    return initialException.get();
  }
}
