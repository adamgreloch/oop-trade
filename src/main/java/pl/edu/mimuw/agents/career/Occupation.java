package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;

public abstract class Occupation {
  public static final int[] EARLY_LEVEL_BUFFS = {50, 150, 300};
  public static final int CUMULATIVE_THRESHOLD = 4;
  public static final int BASE_BUFF_AFTER_THRESHOLD = 300;
  public static final int CUMULATIVE_BUFF = 25;

  public Occupation() {
  }

  public ProductivityVector getBuffValueForLevel(int level) {
    if (level < CUMULATIVE_THRESHOLD)
      return makeBuff(EARLY_LEVEL_BUFFS[level - 1]);
    else
      return makeBuff(BASE_BUFF_AFTER_THRESHOLD +
              (level - CUMULATIVE_THRESHOLD) * CUMULATIVE_BUFF);

  }

  protected abstract ProductivityVector makeBuff(int buffValue);
}
