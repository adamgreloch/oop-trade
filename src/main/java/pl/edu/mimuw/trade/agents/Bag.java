package pl.edu.mimuw.trade.agents;

import com.google.gson.JsonArray;
import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;

import java.util.*;

public class Bag implements ProductivityBuff {
  /**
   * Stores distinguishable products such as clothes, tools and programs.
   */
  protected Map<Product, Map<Integer, LinkedList<Product>>> contents;
  private Worker workerOwner;

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
    if (products == null) return;
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
    return this.contents.get(product.generalize()).containsKey(product.level());
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

  private JsonArray perLevelQuantities(Product product) {
    JsonArray jsonArray = new JsonArray();
    if (!this.contains(product)) return jsonArray;
    Map<Integer, LinkedList<Product>> levelsMap = findProduct(product);
    int maxLevel = 0;
    for (Integer level : levelsMap.keySet())
      maxLevel = Math.max(maxLevel, level);

    for (int i = 1; i <= maxLevel; i++) {
      if (levelsMap.containsKey(i))
        jsonArray.add(levelsMap.get(i).size());
      else
        jsonArray.add(0);
    }

    return jsonArray;
  }

  public JsonArray perLevelTools() {
    return perLevelQuantities(ProductFactory.tool);
  }

  public JsonArray perLevelPrograms() {
    return perLevelQuantities(ProductFactory.program);
  }

  public JsonArray perLevelClothes() {
    return perLevelQuantities(ProductFactory.clothes);
  }

  public int countAll(Product product) {
    if (!this.contains(product)) return 0;
    int quantity = 0;
    for (LinkedList<Product> products : findProduct(product).values())
      quantity += products.size();
    return quantity;
  }

  public int countClothes() {
    return countAll(ProductFactory.clothes);
  }

  public int countPrograms() {
    return countAll(ProductFactory.program);
  }

  public int countTools() {
    return countAll(ProductFactory.tool);
  }

  public int countFood() {
    Product key = ProductFactory.food;
    if (!this.contains(key)) return 0;
    int count = 0;
    for (Product p : findAlike(key))
      count += ((Food) p).quantity();
    return count;
  }

  public double countDiamonds() {
    Product key = ProductFactory.diamond;
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
    Product key = ProductFactory.clothes;
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

  /**
   * @return Program iterator in order from the highest levelled program to lowest.
   */
  public Iterator<Program> programsIterator() {
    SortedSet<Program> programs = new TreeSet<>(Collections.reverseOrder(Program::compareTo)); // Highest level first
    Map<Integer, LinkedList<Product>> programMap = findProduct(ProductFactory.program);

    for (LinkedList<Product> products : programMap.values())
      products.forEach(e -> programs.add((Program) e));

    return programs.iterator();
  }

  public List<ProductivityBuff> listBuffableProducts() {
    List<ProductivityBuff> res = new LinkedList<>();

    for (Product buffable : ProductFactory.previewBuffable())
      for (LinkedList<Product> productList : findProduct(buffable).values())
        productList.forEach(e -> res.add((ProductivityBuff) e));

    res.add(this);
    return res;
  }

  public void setWorkerOwner(Worker workerOwner) {
    this.workerOwner = workerOwner;
  }

  public ProductivityVector getBuffValue() {
    assert workerOwner.starvationLevel() < Worker.DEATH_THRESHOLD;

    ProductivityVector buff = new ProductivityVector();

    if (this.countClothes() < Clothes.NO_CLOTHES_THRESHOLD)
      buff = buff.add(Simulation.noClothesPenalty());

    switch (workerOwner.starvationLevel()) {
      case 1:
        buff = buff.add(Food.MINOR_STARVATION_PENALTY);
        break;
      case 2:
        buff = buff.add(Food.MAJOR_STARVATION_PENALTY);
        break;
    }

    return buff;
  }

  public void wearClothes() {
    int toWear = Worker.DAILY_CLOTHES_CONSUMPTION;
    List<Clothes> notWorn = listClothes();
    if (notWorn == null) return;
    if (notWorn.size() <= toWear) {
      findProduct(ProductFactory.clothes).clear();
      return;
    }
    Collections.shuffle(notWorn);
    for (Clothes clothes : notWorn) {
      if (toWear == 0) break;
      wearWhileCheckingCondition(clothes);
      toWear--;
    }
  }

  private void wearWhileCheckingCondition(Clothes toWear) {
    if (toWear.wearOnce() == 0) this.remove(toWear);
  }

  public void useAllTools() {
    Product key = ProductFactory.tool;
    if (contains(key))
      findProduct(key).clear();
  }

  @Override
  public String toString() {
    return "food: " + countFood()
            + ", clothes: " + countClothes()
            + ", tools: " + countTools()
            + ", diamonds: " + countDiamonds()
            + ", programs: " + countPrograms();
  }
}
