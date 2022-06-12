package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.products.Product;

public class OccupationFactory {
  public static final Craftsman craftsman = new Craftsman();
  public static final Engineer engineer = new Engineer();
  public static final Farmer farmer = new Farmer();
  public static final Miner miner = new Miner();
  public static final Programmer programmer = new Programmer();
  private static final Occupation[] occupations = {farmer, craftsman, engineer, miner, programmer};

  public static Occupation findOccupationByProduct(Product product) {
    for (Occupation occupation : occupations)
      if (occupation.produceBuffedProduct(1).generalize().equals(product.generalize()))
        return occupation;
    throw new IllegalArgumentException("Occupation related to " + product + " doesn't exist");
  }
}