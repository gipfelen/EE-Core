package at.uibk.dps.ee.core.enactable;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class EnactmentFunctionDecoratorTest {

  protected static class TestedDecorator extends EnactmentFunctionDecorator {

    public TestedDecorator(EnactmentFunction decoratedFunction) {
      super(decoratedFunction);
    }

    @Override
    protected JsonObject preprocess(JsonObject input) {
      return input;
    }

    @Override
    protected JsonObject postprocess(JsonObject result) {
      return result;
    }
  }

  @Test
  public void test() {
    String id = "id";
    String type = "type";
    EnactmentFunction mockOriginal = mock(EnactmentFunction.class);
    when(mockOriginal.getId()).thenReturn(id);
    when(mockOriginal.getType()).thenReturn(type);
    JsonObject input = new JsonObject();
    JsonObject result = new JsonObject();
    try {
      when(mockOriginal.processInput(input)).thenReturn(result);
    } catch (StopException e) {
      fail();
    }
    TestedDecorator tested = new TestedDecorator(mockOriginal);
    TestedDecorator spy = spy(tested);
    assertEquals(id, tested.getId());
    assertEquals(type, tested.getType());
    try {
      assertEquals(result, spy.processInput(input));
      verify(spy).preprocess(input);
      verify(spy).postprocess(result);
    } catch (StopException e) {
      fail();
    }
  }
}
