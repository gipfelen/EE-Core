package at.uibk.dps.ee.core;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactmentStateListener;

public class EeCoreAbstractTest {

	protected class EeCoreMock extends EeCore{
		public EeCoreMock(InputDataProvider inputDataProvider, OutputDataHandler outputDataHandler,
				WorkflowProvider applicationProvider, Set<EnactmentStateListener> stateListeners) {
			super(inputDataProvider, outputDataHandler, applicationProvider, stateListeners);
		}
	}
	
	@Test
	public void test() {
		assertEquals(3, 2 + 1);
	}

}
