package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Diamond;
import pl.edu.mimuw.trade.products.Product;

public class Miner extends Occupation {

  public Miner() {
    super("gornik");
  }

  public Occupation copyOf() {
    return new Miner();
  }

  public Product produceBuffedProduct(int level) {
    return new Diamond(1);
  }
}
