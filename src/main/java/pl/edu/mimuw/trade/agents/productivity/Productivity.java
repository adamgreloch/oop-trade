package pl.edu.mimuw.trade.agents.productivity;

import pl.edu.mimuw.trade.simulation.Simulation;

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
    this.totalBuff.clear();
    for (ProductivityBuff buff : this.buffs)
      this.totalBuff = this.totalBuff.add(buff.getBuffValue());
    this.lastUpdated = Simulation.day();
  }

  private ProductivityVector getTotalBuff() {
    if (this.lastUpdated < Simulation.day())
      this.updateBuffs();
    return this.totalBuff;
  }

  public ProductivityVector get() {
    return this.base.add(this.getTotalBuff());
  }

  public int foodBase() {
    return this.base.food();
  }

  public int clothesBase() {
    return this.base.clothes();
  }

  public int toolsBase() {
    return this.base.tools();
  }

  public int diamondsBase() {
    return this.base.diamonds();
  }

  public int programsBase() {
    return this.base.programs();
  }
}
