package osmo.tester.model.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Input/Output in form of characters.
 *
 * @author Teemu Kanstren
 */
public class CharSet extends SearchableInput<Character> {
  /** The input strategy to choose a char. */
  private DataGenerationStrategy strategy = DataGenerationStrategy.RANDOM;
  /** Defines the list of characters for generating valid items. */
  private String validChars = "abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ0123456789,.<>!\"#%&/()=?´`{[]}\\¨^~';:|-_*-+= ";
  /** Defines the list of characters for generating invalid items. */
  private String invalidChars = "";
  /** Index when looped strategy is used. */
  private int loopIndex = 0;

  public CharSet() {
    //we fill assumed set of ASCII invalid characters
    for (int i = 0 ; i <= 32 ; i++) {
      invalidChars += (char) i;
    }
    for (int i = 127 ; i <= 258 ; i++) {
      invalidChars += (char) i;
    }
  }

  @Override
  public CharSet setStrategy(DataGenerationStrategy strategy) {
    switch (strategy) {
      case RANDOM:
      case ORDERED_LOOP:
      case ORDERED_LOOP_INVALID:
      case RANDOM_INVALID:
        this.strategy = strategy;
        loopIndex = 0;
        return this;
      default:
        String name = CharSet.class.getSimpleName();
        String msg = name + " only supports Random, Looping, and Invalid generation strategies. Given:" + strategy;
        throw new UnsupportedOperationException(msg);
    }
  }

  /**
   * Removes the characters in the given string from the potential valid characters to generate, and adds them
   * to the invalid set.
   *
   * @param charsToRemove The characters to remove.
   */
  public void reduceBy(String charsToRemove) {
    char[] r = charsToRemove.toCharArray();
    String result = "";
    String removed = "";
    char[] array = validChars.toCharArray();
    for (char c : array) {
      boolean found = false;
      for (char rc : r) {
        if (c == rc) {
          found = true;
          break;
        }
      }
      if (!found) {
        result += c;
      } else {
        removed += c;
      }
    }
    validChars = result;
    invalidChars += removed;
  }

  /** @return Next generated character. */
  public Character next() {
    switch (strategy) {
      case RANDOM:
        return random();
      case ORDERED_LOOP:
        return loop();
      case RANDOM_INVALID:
        return invalidRandom();
      case ORDERED_LOOP_INVALID:
        return invalidLoop();
      default:
        String name = CharSet.class.getSimpleName();
        throw new IllegalArgumentException("DataGenerationStrategy " + strategy + " not supported by " + name + ".");
    }
  }

  public Character random() {
    int min = 0;
    int max = validChars.length() - 1;
    int index = rand.nextInt(min, max);
    return validChars.charAt(index);
  }

  public Character loop() {
    char c = validChars.charAt(loopIndex);
    loopIndex++;
    if (loopIndex >= validChars.length()) {
      loopIndex = 0;
    }
    return c;
  }

  public Character invalidRandom() {
    int min = 0;
    int max = invalidChars.length() - 1;
    int index = rand.nextInt(min, max);
    return invalidChars.charAt(index);
  }

  public Character invalidLoop() {
    loopIndex++;
    if (loopIndex >= invalidChars.length()) {
      loopIndex = 0;
    }
    return invalidChars.charAt(loopIndex);
  }

  /**
   * @param c Character to test.
   * @return True if given character is in the set defined in this set.
   */
  public boolean evaluate(char c) {
    return validChars.indexOf(c) >= 0;
  }

  /**
   * Removes the XML tag start and end characters from generation ("<" and ">") to allow for the
   * generation of data that can be embedded in XML files.
   * Note that it can still produce some invalid options, so be careful out there..
   */
  public void enableXml() {
    reduceBy("<>");
  }

  /** Sets the set of generated characters to contain only a-z,A-Z,0-9. */
  public void asciiLettersAndNumbersOnly() {
    validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    invalidChars += "åäöÅÄÖ,.<>!\"#%&/()=?´`{[]}\\¨^~';:|-_*-+= ";
  }

  /** Sets the set of generated characters to contain only a-z,A-Z,0-9. */
  public void numbersOnly() {
    validChars = "0123456789";
    invalidChars += "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZåäöÅÄÖ,.<>!\"#%&/()=?´`{[]}\\¨^~';:|-_*-+= ";
  }

  @Override
  public Collection<Character> getOptions() {
    List<Character> result = new ArrayList<>();
    char[] temp = validChars.toCharArray();
    if (strategy == DataGenerationStrategy.ORDERED_LOOP_INVALID || strategy == DataGenerationStrategy.RANDOM_INVALID) {
      temp = invalidChars.toCharArray();
    }
    for (char c : temp) {
      result.add(c);
    }
    return result;
  }

  @Override
  public void enableGUI() {
  }
}
