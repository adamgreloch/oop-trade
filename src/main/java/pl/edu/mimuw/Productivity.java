package pl.edu.mimuw;

import java.util.HashSet;
import java.util.Set;

public class Productivity {
  private final ProductivityVector base;

  /** sum of modifiers
   *  i.e. totalModifier for modifiers -10%, 20%, 40% is equal 50.
   */
  private ProductivityVector totalModifier;
  private int lastUpdated = 0;

  private final Set<ProductivityModifier> modifiers;

  public Productivity(int baseFood, int baseClothes, int baseTools, int baseDiamonds, int basePrograms) {
    this.base = new ProductivityVector(baseFood, baseClothes, baseTools, baseDiamonds, basePrograms);
    this.modifiers = new HashSet<>();
    this.totalModifier = new ProductivityVector();
  }

  public void addModifier(ProductivityModifier modifier) {
    this.modifiers.add(modifier);
  }

  public ProductivityVector get() {
    return base.add(getTotalModifier());
  }

  private ProductivityVector getTotalModifier() {
    if (lastUpdated < Simulation.day()) {
      totalModifier.clear();
      for (ProductivityModifier modifier : modifiers)
        totalModifier = totalModifier.add(modifier.getModifierValue());
      lastUpdated = Simulation.day();
    }
    return totalModifier;
  }

  public int food() {
    return base.food() * (1 + getTotalModifier().food() / 100);
  }

  public int clothes() {
    return base.clothes() * (1 + getTotalModifier().clothes() / 100);
  }

  public int tools() {
    return base.tools() * (1 + getTotalModifier().tools() / 100);
  }

  public int diamonds() {
    return base.diamonds() * (1 + getTotalModifier().diamonds() / 100);
  }

  public int programs() {
    return base.programs() * (1 + getTotalModifier().programs() / 100);
  }
}
