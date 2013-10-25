package osmo.tester.testmodels;

import osmo.tester.annotation.Post;
import osmo.tester.annotation.Pre;
import osmo.tester.annotation.Transition;
import osmo.tester.annotation.Variable;
import osmo.tester.generator.testsuite.TestSuite;

/** @author Teemu Kanstren */
public class ValidTestModel6 {
  private TestSuite history = null;
  private String states = "";
  @Variable
  private int index = 0;

  @Transition("t1")
  public void one() {
    index = 1;
  }

  @Transition("t2")
  public void two() {
    index = 2;
  }

  @Transition("t3")
  public void three() {
    index = 3;
  }

  @Transition("t4")
  public void four() {
    index = 4;
  }

  @Pre("all")
  public void savePreState() {
    states += ":"+history.getCurrentTest().getCurrentStep().getValuesFor("my-state")+":";
  }

  @Post("all")
  public void savePostState() {
    states += "-"+history.getCurrentTest().getCurrentStep().getValuesFor("my-state")+"-";
  }

  public String getStates() {
    return states;
  }
}
