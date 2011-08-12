package osmo.tester.generator.endcondition;

import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;

/**
 * Allows one to compose several end conditions into a single one, where one of the conditions must be met before
 * test generation should end.
 *
 * @author Teemu Kanstren
 */
public class OrComposition implements EndCondition {
  /** The set of end conditions to be checked. */
  private final EndCondition[] conditions;

  /**
   * Costructor.
   *
   * @param conditions The set of end conditions to be checked.
   */
  public OrComposition(EndCondition... conditions) {
    this.conditions = conditions;
  }

  @Override
  public boolean endSuite(TestSuite suite, FSM fsm) {
    for (EndCondition condition : conditions) {
      if (condition.endSuite(suite, fsm)) {
        //if any return true, the OR composition becomes true as well
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean endTest(TestSuite suite, FSM fsm) {
    for (EndCondition condition : conditions) {
      if (condition.endTest(suite, fsm)) {
        //if any return true, the OR composition becomes true as well
        return true;
      }
    }
    return false;
  }
}
