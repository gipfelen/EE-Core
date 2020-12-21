package at.uibk.dps.ee.core;

/**
 * Enum defining the possible states of the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
public enum EnactmentState {
	/**
	 * The enactment is running
	 */
	RUNNING,
	/**
	 * The enactment is paused (can be continued)
	 */
	PAUSED,
	/**
	 * The enactment is stopped (termination after the running enactables finish.)
	 */
	STOPPED

}
