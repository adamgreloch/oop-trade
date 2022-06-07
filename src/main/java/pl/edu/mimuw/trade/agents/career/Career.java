package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;

import java.util.HashMap;
import java.util.Map;

public class Career implements ProductivityBuff {
  private final Map<Occupation, Integer> careers; // Value indicates advancement level
  private Occupation current;

  public Career(Occupation current, int currentLevel) {
    this.current = current;
    this.careers = new HashMap<>();
    this.careers.put(current, currentLevel);
  }

  public void changeOccupation(Occupation next) {
    if (!careers.containsKey(next))
      careers.put(next, 1);

    current = next;
  }

  public int occupationLevel() {
    return careers.get(current);
  }

  public Product currentProduct() {
    return current.produceBuffedProduct(occupationLevel());
  }

  public void advanceLevel() {
    careers.put(current, occupationLevel() + 1);
  }

  public ProductivityVector getBuffValue() {
    return current.getBuffVector(occupationLevel());
  }
}
