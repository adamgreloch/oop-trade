package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityBuff;
import pl.edu.mimuw.ProductivityVector;

import java.util.HashMap;
import java.util.Map;

public class Career implements ProductivityBuff {
  private final Map<Occupation, Integer> careers; // Value indicates advancement level
  private Occupation current;

  public Career(Occupation current) {
    this.current = current;
    this.careers = new HashMap<>();
    this.careers.put(current, 1);
  }

  public void changeOccupation(Occupation next) {
    if (!careers.containsKey(next))
      careers.put(next, 1);

    current = next;
  }

  public int occupationLevel() {
    return careers.get(current);
  }

  public void advanceLevel() {
    careers.put(current, occupationLevel() + 1);
  }

  public ProductivityVector getBuffValue() {
    return current.getBuffValueForLevel(occupationLevel());
  }
}