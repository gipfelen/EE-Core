package at.uibk.dps.ee.core.enactable;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.core.exception.StopException;

import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class EnactableTest {

  @Test
  public void testPause() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    Enactable test = new Enactable(listeners);
    Enactable tested = spy(test);
    tested.pause();
    assertEquals(Enactable.State.PAUSED, tested.getState());
    verify(tested).myPause();
  }

  @Test
  public void testReset() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    Enactable test = new Enactable(listeners);
    Enactable tested = spy(test);
    tested.reset();
    assertEquals(Enactable.State.WAITING, tested.getState());
    verify(tested).myReset();
  }

  @Test
  public void testInit() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    EnactableStateListener listenerMock = mock(EnactableStateListener.class);
    listeners.add(listenerMock);
    Enactable test = new Enactable(listeners);
    Enactable tested = spy(test);
    assertEquals(State.WAITING, tested.state);
    tested.init();
    assertEquals(State.SCHEDULABLE, tested.state);
    verify(tested).myInit();
  }

  @Test
  public void testSetState() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    EnactableStateListener listenerMock = mock(EnactableStateListener.class);
    listeners.add(listenerMock);
    Enactable tested = new Enactable(listeners);
    assertEquals(State.WAITING, tested.state);
    tested.setState(State.STOPPED);
    verify(listenerMock).enactableStateChanged(tested, State.WAITING, State.STOPPED);
  }

  @Test
  public void testPlayCorrect() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    EnactableStateListener listenerMock = mock(EnactableStateListener.class);
    listeners.add(listenerMock);
    Enactable test = new Enactable(listeners);
    Enactable tested = spy(test);
    EnactmentFunction functionMock = mock(EnactmentFunction.class);
    JsonObject input = new JsonObject();
    JsonObject output = new JsonObject();
    tested.jsonInput = input;
    tested.init();
    try {
      when(functionMock.processInput(input)).thenReturn(output);
      tested.schedule(functionMock);
      tested.play();
      assertEquals(State.FINISHED, tested.state);
      verify(tested).setState(State.RUNNING);
    } catch (StopException stopExc) {
      fail();
    }
  }

  @Test
  public void testSchedule() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    Enactable tested = new Enactable(listeners);
    EnactmentFunction function = mock(EnactmentFunction.class);
    tested.setState(State.SCHEDULABLE);
    tested.schedule(function);
    assertEquals(State.LAUNCHABLE, tested.getState());
    assertEquals(tested.enactmentFunction, function);
  }

  @Test(expected = IllegalStateException.class)
  public void testScheduleNotReady() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    Enactable tested = new Enactable(listeners);
    EnactmentFunction function = mock(EnactmentFunction.class);
    tested.schedule(function);
  }

  @Test
  public void testSetInputValue() {
    Enactable tested = new Enactable(new HashSet<>());
    assertNull(tested.jsonInput);
    JsonElement element = new JsonPrimitive(true);
    String key = "key";
    tested.setInputValue(key, element);
    assertNotNull(tested.jsonInput);
    assertEquals(element, tested.jsonInput.get(key));
  }

  @Test
  public void testToString() {
    JsonObject input = new JsonObject();
    JsonElement elementin = new JsonPrimitive("in");
    input.add("key", elementin);
    JsonObject result = new JsonObject();
    JsonElement elementOut = new JsonPrimitive(true);
    result.add("key2", elementOut);
    String expectedEmpty =
        "in: " + new JsonObject().toString() + " out: " + new JsonObject().toString();
    String expectedNotEmpty = "in: " + input.toString() + " out: " + result.toString();
    Enactable tested = new Enactable(new HashSet<>());
    assertEquals(expectedEmpty, tested.toString());
    tested.jsonInput = input;
    tested.jsonResult = result;
    assertEquals(expectedNotEmpty, tested.toString());
  }

  @Test
  public void testPlayStop() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    EnactableStateListener listenerMock = mock(EnactableStateListener.class);
    listeners.add(listenerMock);
    Enactable test = new Enactable(listeners);
    Enactable tested = spy(test);
    tested.init();
    EnactmentFunction function = mock(EnactmentFunction.class);
    tested.schedule(function);
    tested.jsonInput = new JsonObject();
    try {
      Mockito.doThrow(StopException.class).when(function).processInput(any(JsonObject.class));
      tested.play();
      fail();
    } catch (StopException stopExc) {
      assertEquals(State.STOPPED, tested.state);
      verify(tested).setState(State.RUNNING);
    }
  }

  @Test
  public void testPlayNotReady() {
    Set<EnactableStateListener> listeners = new HashSet<>();
    EnactableStateListener listenerMock = mock(EnactableStateListener.class);
    listeners.add(listenerMock);
    Enactable tested = new Enactable(listeners);
    try {
      tested.play();
      fail();
    } catch (StopException e) {
    }
  }

  @Test
  public void testGetResult() {
    Enactable tested = new Enactable(new HashSet<>());
    tested.state = State.FINISHED;
    JsonObject result = new JsonObject();
    tested.jsonResult = result;
    assertEquals(result, tested.getResult());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetResultNotReady() {
    Enactable tested = new Enactable(new HashSet<>());
    tested.state = State.WAITING;
    JsonObject result = new JsonObject();
    tested.jsonResult = result;
    tested.getResult();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetResultNotSet() {
    Enactable tested = new Enactable(new HashSet<>());
    tested.state = State.FINISHED;
    tested.getResult();
  }

  @Test
  public void testGetInput() {
    Enactable tested = new Enactable(new HashSet<>());
    JsonObject input = new JsonObject();
    tested.jsonInput = input;
    assertEquals(input, tested.getInput());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetInputNotSet() {
    Enactable tested = new Enactable(new HashSet<>());
    tested.getInput();
  }
}
