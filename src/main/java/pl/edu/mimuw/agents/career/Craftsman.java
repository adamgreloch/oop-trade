package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Clothes;
import pl.edu.mimuw.products.Product;

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
