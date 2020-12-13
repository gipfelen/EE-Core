package at.uibk.dps.ee.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

import at.uibk.dps.ee.core.exception.StopException.StoppingReason;

public class StopExceptionTest {

	@Test
	public void testGetStoppingReason() {
		StoppingReason reason = StoppingReason.ERROR;
		StopException tested = new StopException(reason);
		assertEquals(StoppingReason.ERROR, tested.getStoppingReason());
	}

}
