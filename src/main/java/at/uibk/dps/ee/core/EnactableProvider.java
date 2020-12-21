package at.uibk.dps.ee.core;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableRoot;

/**
 * The {@link EnactableProvider} is used to obtain the {@link Enactable} of
 * the overall workflow.
 * 
 * @author Fedor Smirnov
 */
public interface EnactableProvider {

	/**
	 * Returns an enactable application.
	 * 
	 * @return the enactable application
	 */
	EnactableRoot getEnactableApplication();
}
