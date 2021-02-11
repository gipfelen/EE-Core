package at.uibk.dps.ee.core.enactable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import java.util.HashSet;
import org.junit.Test;
import at.uibk.dps.ee.core.enactable.Enactable.State;

public class EnactableRootTest {

  @Test
  public void test() {
    EnactmentFunction functionMock = mock(EnactmentFunction.class);
    EnactableRoot mockEnactable = new EnactableRoot(new HashSet<>(), functionMock);
    assertEquals(State.LAUNCHABLE, mockEnactable.getState());
    assertEquals(functionMock, mockEnactable.enactmentFunction);
  }
}
