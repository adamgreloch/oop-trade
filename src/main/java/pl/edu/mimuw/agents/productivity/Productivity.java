package pl.edu.mimuw.agents.productivity;

import pl.edu.mimuw.stock.Simulation;

import java.util.HashSet;
import java.util.Set;

public class Productivity {
  private final ProductivityVector base;
  private final Set<ProductivityBuff> buffs;
  /**
   * sum of buffs
   * i.e. totalBuff for buffs -10%, 20%, 40% is equal 50.
   */
  private ProductivityVector totalBuff;
  private int lastUpdated = 0;

  public Productivity(int baseFood, int baseClothes, int baseTools, int baseDiamonds, int basePrograms) {
    this.base = new ProductivityVector(baseFood, baseClothes, baseTools, baseDiamonds, basePrograms);
    this.buffs = new HashSet<>();
    this.totalBuff = new ProductivityVector();
  }

  public void clearBuffs() {
    this.buffs.clear();
  }

  public void addBuff(ProductivityBuff buff) {
    this.buffs.add(buff);
  }

  public void updateBuffs() {
    totalBuff.clear();
    for (ProductivityBuff buff : buffs)
      totalBuff = totalBuff.add(buff.getBuffValue());
    lastUpdated = Simulation.day();
  }

  private ProductivityVector getTotalBuff() {
    if (lastUpdated < Simulation.day())
      updateBuffs();
    return totalBuff;
  }

  public ProductivityVector get() {
    return base.add(getTotalBuff());
  }

  public int food() {
    return base.food() * (1 + getTotalBuff().food() / 100);
  }

  public int clothes() {
    return base.clothes() * (1 + getTotalBuff().clothes() / 100);
  }

  public int tools() {
    return base.tools() * (1 + getTotalBuff().tools() / 100);
  }

  public int diamonds() {
    return base.diamonds() * (1 + getTotalBuff().diamonds() / 100);
  }

  public int programs() {
    return base.programs() * (1 + getTotalBuff().programs() / 100);
  }
}
