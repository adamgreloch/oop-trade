package pl.edu.mimuw.trade.agents.career;

import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;
import pl.edu.mimuw.trade.products.Product;

import java.util.Objects;

public abstract class Occupation {
  public static final int[] EARLY_LEVEL_BUFFS = {50, 150, 300};
  public static final int CUMULATIVE_THRESHOLD = 4;
  public static final int BASE_BUFF_AFTER_THRESHOLD = 300;
  public static final int CUMULATIVE_BUFF = 25;
  public final String name;

  public Occupation(String name) {
    this.name = name;
  }

  public int getBuffValue(int level) {
    if (level < CUMULATIVE_THRESHOLD)
      return EARLY_LEVEL_BUFFS[level - 1];
    else
      return BASE_BUFF_AFTER_THRESHOLD +
              (level - CUMULATIVE_THRESHOLD) * CUMULATIVE_BUFF;
  }

  public ProductivityVector getBuffVector(int level) {
    return this.makeBuff(this.getBuffValue(level));
  }

  protected abstract ProductivityVector makeBuff(int buffValue);

  public abstract Product produceBuffedProduct(int level);

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    Occupation that = (Occupation) o;
    return this.name.equals(that.name);
  }

}
