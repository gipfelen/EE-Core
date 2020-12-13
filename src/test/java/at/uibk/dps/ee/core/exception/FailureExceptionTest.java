package at.uibk.dps.ee.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class FailureExceptionTest {

	@Test
	public void testGetCaughtExceptionAndMessage() {
		StopException mockStop = mock(StopException.class);
		FailureException tested = new FailureException(mockStop);
		assertEquals(FailureException.failureMessage, tested.getMessage());
		assertEquals(mockStop, tested.getCaughtException());
	}
}
