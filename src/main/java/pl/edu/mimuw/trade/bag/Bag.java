package pl.edu.mimuw.trade.bag;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.products.*;

import java.util.*;

public class Bag {
  protected Map<Integer, LinkedList<Clothes>> clothes;
  protected Map<Integer, LinkedList<Tool>> tools;
  protected Map<Integer, LinkedList<Program>> programs;
  private int foodAmount = 0;
  private double diamondsAmount = 0;

  public Bag() {
    this.clothes = new HashMap<>();
    this.tools = new HashMap<>();
    this.programs = new HashMap<>();
  }

  public void storeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    foodAmount += amount;
  }

  public void storeDiamonds(double amount) {
    if (amount < 0) throw new IllegalArgumentException();
    diamondsAmount += amount;
  }

  /**
   * Takes food from a Bag.
   *
   * @param amount amount of food to be taken
   * @return how much food left in a Bag
   */
  public int takeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    int foodLeft = foodAmount - amount;
    foodAmount = Math.max(foodLeft, 0);
    return foodLeft;
  }

  public double takeDiamonds(double amount) {
    if (amount < 0) throw new IllegalArgumentException();
    double diamondsLeft = diamondsAmount - amount;
    diamondsAmount = Math.max(diamondsLeft, 0);
    return diamondsLeft;
  }

  public int quantity(TradeableProduct product) {
    if (product instanceof Food) return countFood();
    if (product instanceof Clothes && clothes.containsKey(product.level()))
      return clothes.get(product.level()).size();
    if (product instanceof Tool && tools.containsKey(product.level()))
      return tools.get(product.level()).size();
    if (product instanceof Program && programs.containsKey(product.level()))
      return programs.get(product.level()).size();
    return 0;
  }

  public void storeNewProducts(Product product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    if (product instanceof Food) storeFood(quantity); // TODO should add to saleBag not storageBag!!!
    if (product instanceof Diamond) storeDiamonds(quantity);
    if (product instanceof Clothes) storeClothes(product.level(), quantity);
    if (product instanceof Tool) storeTools(product.level(), quantity);
    if (product instanceof Program) storePrograms(product.level(), quantity);
  }

  public void storePurchasedProducts(TradeableProduct product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    if (product instanceof Food) storeFood(quantity);
    if (product instanceof Clothes) addClothes(product);
    if (product instanceof Tool) addTool(product);
    if (product instanceof Program) addProgram(product);
  }

  public Set<TradeableProduct> takeProducts(TradeableProduct product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    Set<TradeableProduct> res = new HashSet<>();

    if (product instanceof Food) {
      takeFood(quantity);
      res.add(new Food());
    }

    // TODO deredund
    if (product instanceof Clothes)
      for (int i = 0; i < quantity; i++)
        res.add(this.clothes.get(product.level()).pop());

    if (product instanceof Tool)
      for (int i = 0; i < quantity; i++)
        res.add(this.tools.get(product.level()).pop());

    if (product instanceof Program)
      for (int i = 0; i < quantity; i++)
        res.add(this.programs.get(product.level()).pop());

    return res;
  }

  private void addClothes(TradeableProduct product) {
    if (!this.clothes.containsKey(product.level()))
      this.clothes.put(product.level(), new LinkedList<>());
    this.clothes.get(product.level()).add((Clothes) product);
  }

  private void addTool(TradeableProduct product) {
    if (!this.tools.containsKey(product.level()))
      this.tools.put(product.level(), new LinkedList<>());
    this.tools.get(product.level()).add((Tool) product);
  }

  private void addProgram(TradeableProduct product) {
    if (!this.programs.containsKey(product.level()))
      this.programs.put(product.level(), new LinkedList<>());
    this.programs.get(product.level()).add((Program) product);
  }

  // TODO deredund these setters
  private void storeClothes(int level, int quantity) {
    if (!this.clothes.containsKey(level))
      this.clothes.put(level, new LinkedList<>());
    for (int i = 0; i < quantity; i++)
      this.clothes.get(level).add(new Clothes(level));
  }

  private void storeTools(int level, int quantity) {
    if (!this.tools.containsKey(level))
      this.tools.put(level, new LinkedList<>());
    for (int i = 0; i < quantity; i++)
      this.tools.get(level).add(new Tool(level));
  }

  private void storePrograms(int level, int quantity) {
    if (!this.programs.containsKey(level))
      this.programs.put(level, new LinkedList<>());
    for (int i = 0; i < quantity; i++)
      this.programs.get(level).add(new Program(level));
  }

  public int countFood() {
    return foodAmount;
  }

  public double countDiamonds() {
    return diamondsAmount;
  }

  public void clear() {
    diamondsAmount = 0;
    foodAmount = 0;
    clothes.clear();
    tools.clear();
    programs.clear();
  }

  protected List<Clothes> listClothes() {
    List<Clothes> res = new LinkedList<>();
    for (Integer level : this.clothes.keySet())
      res.addAll(this.clothes.get(level));
    return res;
  }

  public Iterator<Product> iterateThroughLevels() {
    List<Product> res = new LinkedList<>();

    if (foodAmount > 0) res.add(new Food());
    if (diamondsAmount > 0) res.add(new Diamond());

    // TODO deredund
    for (Integer level : this.clothes.keySet())
      res.add(this.clothes.get(level).peek());
    for (Integer level : this.tools.keySet())
      res.add(this.tools.get(level).peek());
    for (Integer level : this.programs.keySet())
      res.add(this.programs.get(level).peek());

    return res.iterator();
  }

  public List<ProductivityBuff> listBuffableProducts() {
    List<ProductivityBuff> res = new LinkedList<>();

    // TODO deredund
    for (Integer level : this.clothes.keySet())
      res.addAll(this.clothes.get(level));
    for (Integer level : this.tools.keySet())
      res.addAll(this.tools.get(level));
    for (Integer level : this.programs.keySet())
      res.addAll(this.programs.get(level));
    return res;
  }

  public int countTradeable(TradeableProduct product) {
    if (product instanceof Food) return foodAmount;
    if (product instanceof Clothes) return this.clothes.get(product.level()).size();
    if (product instanceof Tool) return this.tools.get(product.level()).size();
    if (product instanceof Program) return this.programs.get(product.level()).size();
    throw new IllegalArgumentException("Argument product out of simulation scope");
  }
}
