package at.uibk.dps.ee.core.exception;

/**
 * The {@link FailureException} is thrown in cases where the execution of the
 * workflow cannot be continued.
 * 
 * @author Fedor Smirnov
 *
 */
public class FailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String message;

	public FailureException(String message) {
		this.message = message;
	}

}
