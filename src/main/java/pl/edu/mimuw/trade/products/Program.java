package pl.edu.mimuw.trade.products;

public class Program extends LevelledTradeable implements Comparable<Program> {

  public Program(int level) {
    super(level, "programy");
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
