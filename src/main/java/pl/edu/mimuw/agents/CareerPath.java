package pl.edu.mimuw.agents;

import pl.edu.mimuw.ProductivityModifier;
import pl.edu.mimuw.ProductivityVector;

public abstract class CareerPath implements ProductivityModifier {
  protected int level = 1;
  protected boolean active = false;

  void activate() {
    this.active = true;
  }

  void deactivate() {
    this.active = false;
  }

  void levelUp() {
    level++;
  }

  public abstract ProductivityVector getModifierValue();
}
