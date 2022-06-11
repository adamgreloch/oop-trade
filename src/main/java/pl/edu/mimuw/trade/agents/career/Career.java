package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;

import java.util.HashMap;
import java.util.Map;

public class Career implements ProductivityBuff {
  private transient Map<Occupation, Integer> careers; // Value indicates advancement level

  private Occupation current;

  public Career(Occupation current, int currentLevel) {
    this.current = current;
    this.careers = new HashMap<>();
    this.careers.put(current, currentLevel);
  }

  public String current() {
    return this.current.name;
  }

  public void changeOccupation(Occupation next) {
    if (careers == null)
      careers = new HashMap<>();

    if (!careers.containsKey(next))
      careers.put(next, 1);

    current = next;
  }

  public int productionLevel(Product product) {
    Product current = currentProduct();
    if (product.generalize().equals(current)) return current.level();
    else return 1;
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
