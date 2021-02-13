package at.uibk.dps.ee.core.exception;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Test;

public class StopExceptionTest {

  @Test
  public void testGetMessage() {
    String message = "message";
    StopException tested = new StopException(message);
    assertEquals(message, tested.getStopMessage());
    String additionalInfo = "additional";
    String expected = message + "\n" + additionalInfo;
    tested.appendExceptionInformation(additionalInfo);
    assertEquals(expected, tested.getStopMessage());
    assertFalse(tested.hasInitialException());
  }

  @Test
  public void testGetMessageExc() {
    String message = "message";
    IllegalArgumentException exc = new IllegalArgumentException("whatever");
    StopException tested = new StopException(message, exc);
    assertEquals(message, tested.getStopMessage());
    assertTrue(tested.hasInitialException());
    assertEquals(exc, tested.getInitialException());
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetMessageExcNotSet() {
    String message = "message";
    StopException tested = new StopException(message);
    tested.getInitialException();
  }
}
