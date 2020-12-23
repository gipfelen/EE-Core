package at.uibk.dps.ee.core;

import static org.junit.Assert.*;

import org.junit.Test;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableRoot;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;
import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.core.exception.FailureException;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.core.exception.StopException.StoppingReason;

import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

public class EeCoreTest {

	protected class EnactableMock extends EnactableRoot {

		protected JsonObject output;
		protected boolean fails = false;

		protected EnactableMock(Set<EnactableStateListener> stateListeners) {
			super(stateListeners);
		}

		@Override
		protected void myPlay() throws StopException {
			if (!fails) {
				return;
			} else {
				throw new StopException(StoppingReason.ERROR);
			}
		}

		@Override
		protected void myPause() {
		}

		@Override
		protected void myInit() {
		}

		@Override
		public JsonObject getOutput() {
			return output;
		}
		
		@Override
		protected void myReset() {
		}
		
	}

	@Test
	public void testEnactCorrect() {
		InputDataProvider inputProviderMock = mock(InputDataProvider.class);
		OutputDataHandler outputDataHandler = mock(OutputDataHandler.class);
		EnactableProvider enactableProvider = mock(EnactableProvider.class);
		Set<EnactmentStateListener> enactmentListeners = new HashSet<>();
		EnactableMock mockEnactable = new EnactableMock(new HashSet<>());
		JsonObject mockInput = new JsonObject();
		when(enactableProvider.getEnactableApplication()).thenReturn(mockEnactable);
		when(inputProviderMock.getInputData()).thenReturn(mockInput);
		EnactmentStateListener mockListener = mock(EnactmentStateListener.class);
		enactmentListeners.add(mockListener);
		JsonObject mockOutput = new JsonObject();
		mockEnactable.output = mockOutput;
		mockEnactable.setState(State.READY);
		EeCore tested = new EeCore(inputProviderMock, outputDataHandler, enactableProvider, enactmentListeners);
		try {
			tested.enactWorkflow();
			verify(outputDataHandler).handleOutputData(mockOutput);
			verify(mockListener).enactmentStarted();
			verify(mockListener).enactmentTerminated();
		} catch (FailureException stopExc) {
			fail();
		}
	}
	
	@Test
	public void testEnactException() {
		InputDataProvider inputProviderMock = mock(InputDataProvider.class);
		OutputDataHandler outputDataHandler = mock(OutputDataHandler.class);
		EnactableProvider enactableProvider = mock(EnactableProvider.class);
		Set<EnactmentStateListener> enactmentListeners = new HashSet<>();
		EnactableMock mockEnactable = new EnactableMock(new HashSet<>());
		mockEnactable.fails = true;
		JsonObject mockInput = new JsonObject();
		when(enactableProvider.getEnactableApplication()).thenReturn(mockEnactable);
		when(inputProviderMock.getInputData()).thenReturn(mockInput);
		EnactmentStateListener mockListener = mock(EnactmentStateListener.class);
		enactmentListeners.add(mockListener);
		JsonObject mockOutput = new JsonObject();
		mockEnactable.output = mockOutput;
		mockEnactable.setState(State.READY);
		EeCore tested = new EeCore(inputProviderMock, outputDataHandler, enactableProvider, enactmentListeners);
		try {
			tested.enactWorkflow();
			fail();
		} catch (FailureException stopExc) {
			return;
		}
	}

	@Test
	public void testConstructor() {
		InputDataProvider inputProviderMock = mock(InputDataProvider.class);
		OutputDataHandler outputDataHandler = mock(OutputDataHandler.class);
		EnactableProvider wfProvider = mock(EnactableProvider.class);
		Set<EnactmentStateListener> enactmentListeners = new HashSet<>();
		EeCore tested = new EeCore(inputProviderMock, outputDataHandler, wfProvider, enactmentListeners);
		assertEquals(inputProviderMock, tested.inputDataProvider);
		assertEquals(outputDataHandler, tested.outputDataHandler);
		assertEquals(wfProvider, tested.enactableProvider);
		assertEquals(enactmentListeners, tested.stateListeners);
	}
}
