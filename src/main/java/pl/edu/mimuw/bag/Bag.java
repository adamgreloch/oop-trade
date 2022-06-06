package pl.edu.mimuw.bag;

import pl.edu.mimuw.Multiset;
import pl.edu.mimuw.products.*;

public class Bag {
  protected Multiset<Clothes> clothes;
  protected Multiset<Tool> tools;
  protected Multiset<Program> programs;
  private int foodAmount = 0;
  private double diamondsAmount = 0;

  public Bag() {
    this.clothes = new Multiset<>();
    this.tools = new Multiset<>();
    this.programs = new Multiset<>();
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
    if (product instanceof Food) storeFood(quantity);
    if (product instanceof Diamond) storeDiamonds(quantity);
    if (product instanceof Clothes) this.clothes.add((Clothes) product, quantity);
    if (product instanceof Tool) this.tools.add((Tool) product, quantity);
    if (product instanceof Program) this.programs.add((Program) product, quantity);
  }

  public Multiset<Clothes> getClothes() {
    return new Multiset<>(clothes);
  }

  public Multiset<Tool> getTools() {
    return new Multiset<>(tools);
  }

  public Multiset<Program> getPrograms() {
    return new Multiset<>(programs);
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
}
