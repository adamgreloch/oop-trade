package pl.edu.mimuw.trade.products;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductFactory {
  private static final Food food = new Food(1);
  private static final Clothes clothes = new Clothes(1);
  private static final Tool tool = new Tool(1);
  private static final Program program = new Program(1);
  private static final Diamond diamond = new Diamond(1);

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

  public static Set<Product> produceAlike(Product product, int quantity, int level) {
    Set<? extends Product> res = null;
    if (product instanceof Food) res = newFood(quantity);
    if (product instanceof Clothes) res = newClothes(quantity, level);
    if (product instanceof Tool) res = newTools(quantity, level);
    if (product instanceof Program) res = newPrograms(quantity, level);
    if (product instanceof Diamond) res = newDiamonds(quantity);
    return new HashSet<>(res);
  }
}
