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
		ERROR, SYNCHRO
	}

	protected final StoppingReason stoppingReason;

	public StopException(StoppingReason stoppingReason) {
		this.stoppingReason = stoppingReason;
	}

	public StoppingReason getStoppingReason() {
		return stoppingReason;
	}
}
