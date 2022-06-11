package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Diamond;
import pl.edu.mimuw.trade.products.Product;

public class Miner extends Occupation {

  public Miner() {
    super("gornik");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffDiamonds(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Diamond(1);
  }
}
