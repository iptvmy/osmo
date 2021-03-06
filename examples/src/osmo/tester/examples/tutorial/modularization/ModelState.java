package osmo.tester.examples.tutorial.modularization;

import osmo.tester.model.data.ValueRange;
import osmo.tester.model.data.ValueSet;

/** @author Teemu Kanstren */
public class ModelState {
  private int helloCount = 0;
  private int worldCount = 0;
  private ValueSet<String> names = new ValueSet<>("teemu", "bob");
  private ValueSet<String> worlds = new ValueSet<>("mars", "venus");
  private ValueSet<Integer> sizes = new ValueSet<>(1,2,6);
  private ValueRange<Double> ranges = new ValueRange<>(0.1d, 5.2d);

  public ModelState() {
   
  }

  public void reset() {
    helloCount = 0;
    worldCount = 0;
  }

  public boolean canHello() {
    return helloCount == worldCount;
  }

  public String nextName() {
    return names.balanced();
  }

  public int nextSize() {
    return sizes.random();
  }

  public void didHello() {
    helloCount++;
  }

  public boolean canWorld() {
    return helloCount > worldCount;
  }

  public void didWorld() {
    worldCount++;
  }

  public String nextWorld() {
    return worlds.random();
  }

  public double nextRange() {
    return ranges.random();
  }
}
