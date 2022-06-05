package pl.edu.mimuw.agents;

import pl.edu.mimuw.ProductivityVector;

public abstract class Occupation {

  public Occupation() {
  }

  public abstract ProductivityVector getModifierValue(int level);
}
