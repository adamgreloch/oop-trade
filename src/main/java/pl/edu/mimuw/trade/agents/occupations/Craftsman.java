package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Clothes;
import pl.edu.mimuw.trade.products.Product;

public class Craftsman extends Occupation {
  public Craftsman() {
    super("rzemieslnik");
  }

  public Occupation copyOf() {
    return new Craftsman();
  }

  public Product produceBuffedProduct(int level) {
    return new Clothes(level);
  }
}
