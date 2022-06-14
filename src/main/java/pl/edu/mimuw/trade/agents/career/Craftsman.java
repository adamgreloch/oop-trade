package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.products.Clothes;
import pl.edu.mimuw.trade.products.Product;

public class Craftsman extends Occupation {
  public Craftsman() {
    super("rzemieslnik");
  }

  public Product produceBuffedProduct(int level) {
    return new Clothes(level);
  }
}
