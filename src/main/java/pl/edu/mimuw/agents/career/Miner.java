package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Diamond;
import pl.edu.mimuw.products.Product;

public class Miner extends Occupation {
  public Miner() {
    super("miner");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffDiamonds(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Diamond();
  }
}
