package at.uibk.dps.ee.core;

import java.util.Set;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;
import at.uibk.dps.ee.core.exception.FailureException;
import at.uibk.dps.ee.core.exception.StopException;

/**
 * Core class performing the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class EeCoreAbstract {

	protected final InputDataProvider inputDataProvider;
	protected final OutputDataHandler outputDataHandler;
	protected final WorkflowProvider workflowProvider;
	
	protected final Set<EnactmentStateListener> stateListeners;

	public EeCoreAbstract(final InputDataProvider inputDataProvider, final OutputDataHandler outputDataHandler,
			final WorkflowProvider applicationProvider, final Set<EnactmentStateListener> stateListeners) {
		this.inputDataProvider = inputDataProvider;
		this.outputDataHandler = outputDataHandler;
		this.workflowProvider = applicationProvider;
		this.stateListeners = stateListeners;
	}

	/**
	 * Obtains the root enactable from the provider, inits it with the input data,
	 * and triggers the execution. It is expected that the root enactable cannot
	 * throw {@link StopException}.
	 * 
	 * @throws FailureException
	 */
	public void enactWorkflow() throws FailureException {
		final Enactable enactableRoot = workflowProvider.getEnactableApplication();
		JsonObject inputData = inputDataProvider.getInputData();
		enactableRoot.init(inputData);
		for (EnactmentStateListener stateListener : stateListeners) {
			stateListener.enactmentStarted();
		}
		try {
			JsonObject outputData = enactableRoot.play();
			outputDataHandler.handleOutputData(outputData);
		} catch (StopException stopException) {
			// The root should never throw exceptions.
			throw new FailureException("Stop exception from the enactable root.");
		}
		for (EnactmentStateListener stateListener : stateListeners) {
			stateListener.enactmentTerminated();
		}
	}
}
