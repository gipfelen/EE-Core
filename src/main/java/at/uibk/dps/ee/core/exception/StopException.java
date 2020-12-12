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
	 * The reason for the stop.
	 * 
	 * @author Fedor
	 */
	public enum StoppingReason {
		/**
		 * Stopping because of an internal error
		 */
		ERROR
	}

	protected final StoppingReason stoppingReason;

	/**
	 * Constructed by providing the reason for the stop.
	 *  
	 * @param stoppingReason the reason for the stop
	 */
	public StopException(StoppingReason stoppingReason) {
		super();
		this.stoppingReason = stoppingReason;
	}

	public StoppingReason getStoppingReason() {
		return stoppingReason;
	}
}
