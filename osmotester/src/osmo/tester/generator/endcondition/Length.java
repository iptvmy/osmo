package osmo.tester.generator.endcondition;

import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;

/**
 * A simple end condition to stop test case generation when a number of test steps has been generated,
 * or to stop test suite generation when a number of test cases in the suite has been generated.
 * 
 * @author Teemu Kanstren
 */
public class Length implements EndCondition {
  private static Logger log = new Logger(Length.class);
   /** The stopping length. */
  private final int length;

  /**
   * Constructor.
   *
   * @param length The number of steps/tests when to stop generation.
   */
  public Length(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("Length cannot be < 0, was "+length+".");
    }
    this.length = length;
  }

  @Override
  public boolean endSuite(TestSuite suite, FSM fsm) {
    log.debug(" es:"+suite.getTestCases().size()+" c:"+suite.currentSteps());
    return suite.getTestCases().size() >= length;
  }

  @Override
  public boolean endTest(TestSuite suite, FSM fsm) {
    log.debug(" et:"+suite.getTestCases().size()+" c:"+suite.currentSteps());
    return suite.currentSteps() >= length;
  }
}
