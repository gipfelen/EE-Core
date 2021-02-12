package at.uibk.dps.ee.core.exception;

/**
 * The {@link FailureException} is thrown in cases where the execution of the
 * workflow cannot be continued.
 * 
 * @author Fedor Smirnov
 *
 */
public class FailureException extends Exception {

  protected final static String failureMessage =
      "Enactment failed. Stop exception at the root enactable.";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected final Exception caughtException;

  /**
   * Constructed with the caught exception which causes the enactment failure.
   * 
   * @param caughtException the cause for the enactment failure
   */
  public FailureException(final Exception caughtException) {
    super(failureMessage);
    this.caughtException = caughtException;
  }

  public Exception getCaughtException() {
    return caughtException;
  }
}
