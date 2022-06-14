package pl.edu.mimuw.trade.products;

import pl.edu.mimuw.trade.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.trade.agents.productivity.ProductivityVector;

public class Program extends LevelledTradeable implements ProductivityBuff, Comparable<Program> {

  public Program(int level) {
    super(level, "programy");
  }

  @Override
  public ProductivityVector getBuffValue() {
    return new ProductivityVector();
  }

  public int tradePriority() {
    return 3;
  }

  @Override
  public int compareTo(Program other) {
    int cmp = this.level - other.level;
    if (cmp == 0) return this.id - other.id;
    return cmp;
  }
}
