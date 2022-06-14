package pl.edu.mimuw.trade.agents;

import com.google.gson.JsonArray;
import pl.edu.mimuw.trade.products.*;
import pl.edu.mimuw.trade.simulation.Simulation;

import java.util.*;

public class Bag implements ProductivityBuff {
  protected final Map<Product, Map<Integer, LinkedList<Product>>> contents;
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

  public Set<Product> takeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    Product key = new Food(1);
    int toTake = amount;
    while (toTake > 0) {
      if (this.findAlike(key).isEmpty()) return null;
      Food taken = (Food) this.findAlike(key).pop();
      toTake -= taken.quantity();
      if (toTake < 0)
        this.storeProduct(new Food(-toTake));
    }
    return Collections.singleton(new Food(amount));
  }

  public void takeDiamonds(double amount) {
    if (amount < 0) throw new IllegalArgumentException();
    Product key = new Diamond(1);
    double toTake = amount;
    while (toTake > 0) {
      if (this.findAlike(key).isEmpty()) return;
      Diamond taken = (Diamond) this.findAlike(key).pop();
      toTake -= taken.value();
      if (toTake < 0)
        this.storeProduct(new Diamond(-toTake));
    }
  }

  public void storeProducts(Iterable<? extends Product> products) {
    if (products == null) return;
    products.forEach(this::storeProduct);
  }

  private void storeProduct(Product product) {
    if (!this.contents.containsKey(product.generalize()))
      this.contents.put(product.generalize(), new HashMap<>());

    Map<Integer, LinkedList<Product>> perLevel = this.findProduct(product);

    if (!perLevel.containsKey(product.level()))
      perLevel.put(product.level(), new LinkedList<>());

    perLevel.get(product.level()).add(product);
  }

  public boolean contains(Product product) {
    if (product == null) return false;
    if (!this.contents.containsKey(product.generalize())) return false;
    return this.contents.get(product.generalize()).containsKey(product.level());
  }

  public Set<Product> takeProducts(Product product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    if (product instanceof Food) return this.takeFood(quantity);

    if (!this.contains(product)) return null;
    Set<Product> res = new HashSet<>();

    LinkedList<Product> alike = this.findAlike(product);

    for (int i = 0; i < quantity; i++)
      res.add(alike.pop());

    return res;
  }

  protected Map<Integer, LinkedList<Product>> findProduct(Product product) {
    return this.contents.get(product.generalize());
  }

  protected LinkedList<Product> findAlike(Product product) {
    if (this.findProduct(product) == null) return new LinkedList<>();
    return this.findProduct(product).get(product.level());
  }

  public int quantity(Product product) {
    if (!this.contains(product)) return 0;
    if (product instanceof Food) return this.countFood();
    return this.findAlike(product).size();
  }

  protected void remove(Product product) {
    this.findProduct(product).get(product.level()).remove(product);
  }

  public JsonArray perLevelQuantities(Product product) {
    JsonArray jsonArray = new JsonArray();
    if (!this.contains(product)) return jsonArray;
    Map<Integer, LinkedList<Product>> levelsMap = this.findProduct(product);
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

  public int countFood() {
    Product key = ProductFactory.food;
    if (!this.contains(key)) return 0;
    int count = 0;
    for (Product p : this.findAlike(key))
      count += ((Food) p).quantity();
    return count;
  }

  public double countDiamonds() {
    Product key = ProductFactory.diamond;
    if (!this.contains(key)) return 0;
    double value = 0;
    for (Product p : this.findAlike(key))
      value += ((Diamond) p).value();
    return value;
  }

  public void clear() {
    this.contents.clear();
  }

  protected List<Clothes> listClothes() {
    Product key = ProductFactory.clothes;
    if (!this.contains(key)) return null;
    List<Clothes> res = new LinkedList<>();
    for (LinkedList<Product> clothes : this.findProduct(key).values())
      clothes.forEach(e -> res.add((Clothes) e));
    return res;
  }

  public Iterator<Product> iterateThroughLevels() {
    List<Product> res = new LinkedList<>();

    for (Map<Integer, LinkedList<Product>> levels : this.contents.values())
      levels.values().forEach(e -> res.add(e.peek()));

    return res.iterator();
  }

  /**
   * @return Program iterator in order from the highest levelled program to lowest.
   */
  public Iterator<Program> programsIterator() {
    Set<Program> programs = new TreeSet<>(Collections.reverseOrder(Program::compareTo)); // Highest level first
    Map<Integer, LinkedList<Product>> programMap = this.findProduct(ProductFactory.program);

    for (LinkedList<Product> products : programMap.values())
      products.forEach(e -> programs.add((Program) e));

    return programs.iterator();
  }

  public List<ProductivityBuff> listBuffingProducts() {
    List<ProductivityBuff> res = new LinkedList<>();

    for (Product buffing : ProductFactory.previewBuffing())
      for (LinkedList<Product> productList : this.findProduct(buffing).values())
        productList.forEach(e -> res.add((ProductivityBuff) e));

    res.add(this);
    return res;
  }

  public void setWorkerOwner(Worker workerOwner) {
    this.workerOwner = workerOwner;
  }

  public ProductivityVector getBuffValue() {
    assert this.workerOwner.starvationLevel() < Worker.DEATH_THRESHOLD;

    ProductivityVector buff = new ProductivityVector();

    if (this.quantity(ProductFactory.clothes) < Clothes.NO_CLOTHES_THRESHOLD)
      buff = buff.add(-Simulation.noClothesPenalty());

    switch (this.workerOwner.starvationLevel()) {
      case 1:
        buff = buff.add(-Food.MINOR_STARVATION_PENALTY);
        break;
      case 2:
        buff = buff.add(-Food.MAJOR_STARVATION_PENALTY);
        break;
    }

    return buff;
  }

  public void wearClothes() {
    int toWear = Worker.DAILY_CLOTHES_CONSUMPTION;
    List<Clothes> clothes = this.listClothes();
    if (clothes == null) return;
    if (clothes.size() <= toWear) {
      this.findProduct(ProductFactory.clothes).clear();
      return;
    }
    Collections.shuffle(clothes);
    Iterator<Clothes> notWorn = clothes.iterator();
    for (; toWear > 0; toWear--)
      this.wearWhileCheckingCondition(notWorn.next());
  }

  private void wearWhileCheckingCondition(Clothes toWear) {
    if (toWear.wearOnce() == 0) this.remove(toWear);
  }

  public void useAllTools() {
    Product key = ProductFactory.tool;
    if (this.contains(key)) this.findProduct(key).clear();
  }

  public int totalQuantity() {
    int total = 0;
    for (Product product : ProductFactory.previewProducts())
      total += this.quantity(product);
    return total;
  }
}
