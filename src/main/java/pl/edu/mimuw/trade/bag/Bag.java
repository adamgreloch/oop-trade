package pl.edu.mimuw.trade.bag;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.products.*;

import java.util.*;

public class Bag {
  /**
   * Stores distinguishable products such as clothes, tools and programs.
   */
  protected Map<Product, Map<Integer, LinkedList<Product>>> contents;

  public Bag() {
    this.contents = new HashMap<>();
  }

  public void storeFood(int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    this.storeProduct(new Food(quantity));
  }

  public void storeDiamonds(double amount) {
    if (amount < 0) throw new IllegalArgumentException();
    this.storeProduct(new Diamond(amount));
  }

  /**
   * Takes food from a Bag.
   *
   * @param amount amount of food to be taken
   * @return how much food left in a Bag
   */
  public Set<Product> takeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    Product key = new Food(1);
    int toTake = amount;
    while (toTake > 0) {
      if (findAlike(key).isEmpty()) return null;
      Food taken = (Food) findAlike(key).pop();
      toTake -= taken.quantity();
      if (toTake < 0)
        this.storeProduct(new Food(-toTake));
    }
    return Collections.singleton(new Food(amount));
  }

  public Set<Product> takeDiamonds(double amount) {
    if (amount < 0) throw new IllegalArgumentException();
    Product key = new Diamond(1);
    double toTake = amount;
    while (toTake > 0) {
      if (findAlike(key).isEmpty()) return null;
      Diamond taken = (Diamond) findAlike(key).pop();
      toTake -= taken.value();
      if (toTake < 0)
        this.storeProduct(new Diamond(-toTake));
    }
    return Collections.singleton(new Diamond(amount));
  }

  public void storeProducts(Set<? extends Product> products) {
    products.forEach(this::storeProduct);
  }

  private void storeProduct(Product product) {
    if (!this.contents.containsKey(product.generalize()))
      this.contents.put(product.generalize(), new HashMap<>());

    Map<Integer, LinkedList<Product>> perLevel = findProduct(product);

    if (!perLevel.containsKey(product.level()))
      perLevel.put(product.level(), new LinkedList<>());

    perLevel.get(product.level()).add(product);
  }

  public boolean contains(Product product) {
    if (product == null) return false;
    if (!this.contents.containsKey(product.generalize())) return false;
    if (!this.contents.get(product.generalize()).containsKey(product.level())) return false;
    return true;
  }

  /**
   * Assumes there is sufficient amount of products in a Bag to perform operation.
   */
  public Set<Product> takeProducts(Product product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    if (product instanceof Food) return takeFood(quantity);

    if (!this.contains(product)) return null;
    Set<Product> res = new HashSet<>();

    LinkedList<Product> alike = findAlike(product);

    for (int i = 0; i < quantity; i++)
      res.add(alike.pop());

    return res;
  }

  protected Map<Integer, LinkedList<Product>> findProduct(Product product) {
    return this.contents.get(product.generalize());
  }

  protected LinkedList<Product> findAlike(Product product) {
    if (findProduct(product) == null) return new LinkedList<>();
    return findProduct(product).get(product.level());
  }

  public int quantity(Product product) {
    if (!this.contains(product)) return 0;
    if (product instanceof Food) return countFood();
    return findAlike(product).size();
  }

  protected void remove(Product product) {
    findProduct(product).get(product.level()).remove(product);
  }

  public int countFood() {
    Product key = new Food(1);
    if (!this.contains(key)) return 0;
    int count = 0;
    for (Product p : findAlike(key))
      count += ((Food) p).quantity();
    return count;
  }

  public double countDiamonds() {
    Product key = new Diamond(1);
    if (!this.contains(key)) return 0;
    double value = 0;
    for (Product p : findAlike(key))
      value += ((Diamond) p).value();
    return value;
  }

  public void clear() {
    contents.clear();
  }

  protected List<Clothes> listClothes() {
    Product key = new Clothes(1);
    if (!contains(key)) return null;
    List<Clothes> res = new LinkedList<>();
    for (LinkedList<Product> clothes : findProduct(key).values())
      clothes.forEach(e -> res.add((Clothes) e));
    return res;
  }

  public Iterator<Product> iterateThroughLevels() {
    List<Product> res = new LinkedList<>();

    for (Map<Integer, LinkedList<Product>> levels : contents.values())
      levels.values().forEach(e -> res.add(e.peek()));

    return res.iterator();
  }

  public List<ProductivityBuff> listBuffableProducts() {
    List<ProductivityBuff> res = new LinkedList<>();

    for (Product buffable : ProductFactory.previewBuffable())
      for (LinkedList<Product> productList : findProduct(buffable).values())
        productList.forEach(e -> res.add((ProductivityBuff) e));

    return res;
  }
}
