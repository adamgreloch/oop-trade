package pl.edu.mimuw.bag;

import pl.edu.mimuw.products.*;

import java.util.*;

public class Bag implements Iterable<Product> {
  protected Set<Clothes> clothes;
  protected Set<Tool> tools;
  protected Set<Program> programs;
  private int foodAmount = 0;
  private double diamondsAmount = 0;

  public Bag() {
    this.clothes = new HashSet<>();
    this.tools = new HashSet<>();
    this.programs = new HashSet<>();
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

  public void storeProduct(Product product, int quantity) {
    if (quantity < 0) throw new IllegalArgumentException();
    if (product instanceof Food) storeFood(quantity);
    if (product instanceof Diamond) storeDiamonds(quantity);
    if (product instanceof Clothes) storeClothes(product.level(), quantity);
    if (product instanceof Tool) storeTools(product.level(), quantity);
    if (product instanceof Program) storePrograms(product.level(), quantity);
  }

  private void storeClothes(int level, int quantity) {
    for (int i = 0; i < quantity; i++)
      this.clothes.add(new Clothes(level));
  }

  private void storeTools(int level, int quantity) {
    for (int i = 0; i < quantity; i++)
      this.tools.add(new Tool(level));
  }

  private void storePrograms(int level, int quantity) {
    for (int i = 0; i < quantity; i++)
      this.programs.add(new Program(level));
  }

  public Set<Clothes> getClothes() {
    return new HashSet<>(clothes);
  }

  public Set<Tool> getTools() {
    return new HashSet<>(tools);
  }

  public Set<Program> getPrograms() {
    return new HashSet<>(programs);
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

  public Iterator<Product> iterator() {
    List<Product> res = new LinkedList<>();
    if (foodAmount > 0) res.add(new Food());
    if (diamondsAmount > 0) res.add(new Diamond());
    res.addAll(this.clothes);
    res.addAll(this.tools);
    res.addAll(this.programs);
    return res.iterator();
  }

  public int countTradeable(TradeableProduct product) {
    if (product instanceof Food) return foodAmount;
    if (product instanceof Clothes) return this.clothes.size();
    if (product instanceof Tool) return this.tools.size();
    if (product instanceof Program) return this.programs.size();
    throw new IllegalArgumentException("Argument product out of simulation scope");
  }
}
