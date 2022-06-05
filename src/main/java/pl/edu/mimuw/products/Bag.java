package pl.edu.mimuw.products;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Bag {
  protected static Random RANDOM;

  private int foodAmount = 0;
  private int diamondsAmount = 0;

  protected Set<Clothes> clothes;
  protected Set<Tool> tools;
  protected Set<Program> programs;


  public Bag() {
    this.clothes = new HashSet<>();
    this.tools = new HashSet<>();
    this.programs = new HashSet<>();
  }

  public void storeFood(int amount) {
    if (amount < 0) throw new IllegalArgumentException();
    foodAmount += amount;
  }

  public void storeDiamonds(int amount) {
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

  public void storeClothes(Clothes... clothes) {
    this.clothes.addAll(List.of(clothes));
  }

  public void storeTools(Tool... tools) {
    this.tools.addAll(List.of(tools));
  }

  public void storePrograms(Program... programs) {
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
