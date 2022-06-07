package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Clothes;
import pl.edu.mimuw.trade.products.Product;

public class Craftsman extends Occupation {
  public Craftsman() {
    super("craftsman");
  }

  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffClothes(buffValue);
  }

  public Product produceBuffedProduct(int level) {
    return new Clothes(level);
  }
}
