package pl.edu.mimuw.trade.agents.occupations;

import pl.edu.mimuw.trade.agents.Occupation;
import pl.edu.mimuw.trade.products.Product;

import java.lang.reflect.Type;

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

  public static Occupation instanceFromType(Type t) {
    for (Occupation occupation : occupations)
      if (occupation.getClass() == t)
        return occupation.copyOf();
    throw new IllegalArgumentException("Occupation of type \"" + t + "\" doesn't exist");
  }
}
