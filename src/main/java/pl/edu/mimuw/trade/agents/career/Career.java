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
    if (this.careers == null)
      this.careers = new HashMap<>();

    if (!this.careers.containsKey(next))
      this.careers.put(next, 1);

    this.current = next;
  }

  public int productionLevel(Product product) {
    Product current = this.currentProduct();
    if (product.generalize().equals(current)) return current.level();
    else return 1;
  }

  public int occupationLevel() {
    return this.careers.get(this.current);
  }

  public Product currentProduct() {
    return this.current.produceBuffedProduct(this.occupationLevel());
  }

  public void advanceLevel() {
    this.careers.put(this.current, this.occupationLevel() + 1);
  }

  public ProductivityVector getBuffValue() {
    return this.current.getBuffVector(this.occupationLevel());
  }
}
