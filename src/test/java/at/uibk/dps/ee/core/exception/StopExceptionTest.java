package at.uibk.dps.ee.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class StopExceptionTest {

  @Test
  public void testGetMessage() {
    String message = "message";
    StopException tested = new StopException(message);
    assertEquals(message, tested.getMessage());
  }

}
