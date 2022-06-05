package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityModifier;
import pl.edu.mimuw.ProductivityVector;
import pl.edu.mimuw.agents.Worker;

/**
 * All Worker's possessions are stored in a Bag.
 */
public class WorkerBag extends Bag implements ProductivityModifier {
  private static final int MINOR_STARVATION_PENALTY = -100;
  private static final int MAJOR_STARVATION_PENALTY = -300;

  private final Worker owner;

  public WorkerBag(Worker owner) {
    super();
    this.owner = owner;
  }

  public ProductivityVector getModifierValue() {
    assert owner.starvationLevel() < Worker.DEATH_THRESHOLD;

    switch (owner.starvationLevel()) {
      case 1:
        return new ProductivityVector(MINOR_STARVATION_PENALTY);
      case 2:
        return new ProductivityVector(MAJOR_STARVATION_PENALTY);
      default:
        return new ProductivityVector();
    }
  }
}
