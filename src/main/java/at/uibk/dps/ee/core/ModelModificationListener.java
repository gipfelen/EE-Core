package at.uibk.dps.ee.core;

/**
 * Interface used for the classes which have to be notified of changes to the
 * enactment model.
 * 
 * @author Fedor Smirnov
 */
public interface ModelModificationListener {

	/**
	 * Check the current state of the model and react to its changes, if necessary
	 * (Classes implementing the {@link ModelModificationListener} should be capable
	 * of checking the model state).
	 */
	void reactToModelModification();
}
