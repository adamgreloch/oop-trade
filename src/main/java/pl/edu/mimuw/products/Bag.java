package pl.edu.mimuw.products;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bag {
  private int foodAmount = 0;
  private int diamondsAmount = 0;

  private Set<Clothes> clothes;
  private Set<Tool> tools;
  private Set<Program> programs;

  public void putFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    foodAmount += amount;
  }

  public void putDiamonds(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    diamondsAmount += amount;
  }

  public int takeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    int foodLeft = foodAmount - amount;
    foodAmount = Math.max(foodLeft, 0);
    return foodLeft;
  }

  public int takeDiamonds(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    int diamondsLeft = diamondsAmount - amount;
    diamondsAmount = Math.max(diamondsLeft, 0);
    return diamondsLeft;
  }

  public void putClothes(Clothes... clothes) {
    this.clothes.addAll(List.of(clothes));
  }

  public void putTools(Tool... tools) {
    this.tools.addAll(List.of(tools));
  }

  public void putPrograms(Program... programs) {
    this.programs.addAll(List.of(programs));
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

  public void clear() {
    diamondsAmount = 0;
    foodAmount = 0;
    clothes.clear();
    tools.clear();
    programs.clear();
  }
}
