package osmo.tester.examples.tutorial.modularization;

import osmo.tester.annotation.AfterTest;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

/** @author Teemu Kanstren */
public class HelloModule {
  private final ModelState state;

  public HelloModule(ModelState state) {
    this.state = state;
  }

  @BeforeTest
  public void startTest() {
    state.reset();
    System.out.println("TEST START");
  }

  @AfterTest
  public void endTest() {
    System.out.println("TEST END");
  }

  @Guard("hello")
  public boolean thisNameReallyIsIrrelevant() {
    return state.canHello();
  }

  @TestStep("hello")
  public void sayHello() {
    System.out.println("HELLO "+state.nextName()+"("+state.nextSize()+")");
    state.didHello();
  }
}
