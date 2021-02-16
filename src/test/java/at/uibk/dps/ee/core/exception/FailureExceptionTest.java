package at.uibk.dps.ee.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class FailureExceptionTest {

	@Test
	public void testGetCaughtExceptionAndMessage() {
	  String message = "message";
		StopException stopExc = new StopException(message, new IllegalArgumentException());
		FailureException tested = new FailureException(stopExc);
		assertEquals(message, tested.getMessage());
		assertEquals(stopExc, tested.getCause());
	}
}
