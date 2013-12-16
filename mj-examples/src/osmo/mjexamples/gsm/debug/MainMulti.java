package osmo.mjexamples.gsm.debug;

import osmo.common.NullPrintStream;
import osmo.mjexamples.gsm.GSMModelFactory;
import osmo.tester.OSMOConfiguration;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.Time;
import osmo.tester.generator.multi.MultiOSMO;

import java.util.concurrent.TimeUnit;

/**
 * @author Teemu Kanstren
 */
public class MainMulti {
  public static void main(String[] args) {
    MultiOSMO osmo = new MultiOSMO(0x555);
    OSMOConfiguration config = osmo.getConfig();
    config.setFactory(new GSMModelFactory(NullPrintStream.stream));
    config.setTestEndCondition(new Length(50));
    config.setSuiteEndCondition(new Length(20));
    osmo.generate(new Time(10, TimeUnit.HOURS), false, false);
  }
}
