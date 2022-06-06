package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Product;
import pl.edu.mimuw.products.Program;

public class Programmer extends Occupation {
  public Programmer() {
    super("programmer");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffPrograms(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Program(level);
  }
}
