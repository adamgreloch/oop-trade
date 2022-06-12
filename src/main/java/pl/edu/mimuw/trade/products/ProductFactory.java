package pl.edu.mimuw.trade.products;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductFactory {
  public static final Food food = new Food(1);
  public static final Clothes clothes = new Clothes(1);
  public static final Tool tool = new Tool(1);
  public static final Program program = new Program(1);
  public static final Diamond diamond = new Diamond(1);

  private static final Product[] buffable = {clothes, tool, program};
  private static final Tradeable[] tradeable = {food, clothes, tool, program};
  private static final Product[] products = {food, clothes, tool, program, diamond};

  public static Product[] previewProducts() {
    return products.clone();
  }

  public static Tradeable[] previewTradeable() {
    return tradeable.clone();
  }

  public static Product[] previewBuffable() {
    return buffable.clone();
  }

  public static Set<Food> newFood(int quantity) {
    return Collections.singleton(new Food(quantity));
  }

  public static Set<Diamond> newDiamonds(double value) {
    return Collections.singleton(new Diamond(value));
  }

  public static Set<Clothes> newClothes(int quantity, int level) {
    Set<Clothes> res = new HashSet<>();
    for (int i = 0; i < quantity; i++)
      res.add(new Clothes(level));
    return res;
  }

  public static Set<Tool> newTools(int quantity, int level) {
    Set<Tool> res = new HashSet<>();
    for (int i = 0; i < quantity; i++)
      res.add(new Tool(level));
    return res;
  }

  public static Set<Program> newPrograms(int quantity, int level) {
    Set<Program> res = new HashSet<>();
    for (int i = 0; i < quantity; i++)
      res.add(new Program(level));
    return res;
  }

  public static Product produceAlike(Product product, int level) {
    Product res = null;
    if (product instanceof Food) res = new Food(1);
    if (product instanceof Clothes) res = new Clothes(level);
    if (product instanceof Tool) res = new Tool(level);
    if (product instanceof Program) res = new Program(level);
    if (product instanceof Diamond) res = new Diamond(1);
    return res;
  }

  public static Set<Product> produceAlike(Product product, int quantity, int level) {
    Set<? extends Product> res = null;
    if (product instanceof Food) res = newFood(quantity);
    if (product instanceof Clothes) res = newClothes(quantity, level);
    if (product instanceof Tool) res = newTools(quantity, level);
    if (product instanceof Program) res = newPrograms(quantity, level);
    if (product instanceof Diamond) res = newDiamonds(quantity);
    if (res == null) throw new IllegalArgumentException(product + " is not creatable");
    return new HashSet<>(res);
  }
}
