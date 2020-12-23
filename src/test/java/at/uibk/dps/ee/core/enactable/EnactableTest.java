package at.uibk.dps.ee.core.enactable;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.core.exception.StopException;

import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;

public class EnactableTest {

	/**
	 * Returns a mock for the abstract class which is called with the provided
	 * listeners in its constructor.
	 * 
	 * @param listeners the enactable listeners
	 * @return a mock for the abstract class which is called with the provided
	 *         listeners in its constructor
	 */
	protected static Enactable getTested(Set<EnactableStateListener> listeners) {
		return mock(Enactable.class,
				Mockito.withSettings().useConstructor(listeners).defaultAnswer(Mockito.CALLS_REAL_METHODS));
	}

	@Test
	public void testPause() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		Enactable tested = getTested(listeners);
		tested.pause();
		assertEquals(Enactable.State.PAUSED, tested.getState());
		verify(tested).myPause();
	}

	@Test
	public void testReset() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		Enactable tested = getTested(listeners);
		tested.reset();
		assertEquals(Enactable.State.WAITING, tested.getState());
		verify(tested).myReset();
	}

	@Test
	public void testInit() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		listeners.add(listenerMock);
		Enactable tested = getTested(listeners);
		assertEquals(State.WAITING, tested.state);
		tested.init();
		assertEquals(State.READY, tested.state);
		verify(tested).myInit();
	}

	@Test
	public void testSetState() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		listeners.add(listenerMock);
		Enactable tested = getTested(listeners);
		assertEquals(State.WAITING, tested.state);
		tested.setState(State.STOPPED);
		verify(listenerMock).enactableStateChanged(tested, State.WAITING, State.STOPPED);
	}

	@Test
	public void testPlayCorrect() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		listeners.add(listenerMock);
		Enactable tested = getTested(listeners);
		tested.init();
		try {
			tested.play();
			assertEquals(State.FINISHED, tested.state);
			verify(tested).setState(State.RUNNING);
		} catch (StopException stopExc) {
			fail();
		}
	}

	@Test
	public void testPlayStop() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		listeners.add(listenerMock);
		Enactable tested = getTested(listeners);
		tested.init();
		try {
			Mockito.doThrow(StopException.class).when(tested).myPlay();
			tested.play();
			fail();
		} catch (StopException stopExc) {
			assertEquals(State.STOPPED, tested.state);
			verify(tested).setState(State.RUNNING);
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testPlayNotReady() {
		Set<EnactableStateListener> listeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		listeners.add(listenerMock);
		Enactable tested = getTested(listeners);
		try {
			tested.play();
		} catch (StopException e) {
			fail();
		}
	}
}
