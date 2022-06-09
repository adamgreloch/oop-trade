package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;
import pl.edu.mimuw.trade.products.Program;

public class Programmer extends Occupation {
  public Programmer() {
    super("programista");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffPrograms(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Program(level);
  }
}
