package pl.edu.mimuw.products;

import pl.edu.mimuw.ProductivityModifier;
import pl.edu.mimuw.ProductivityVector;
import pl.edu.mimuw.agents.Worker;

import java.util.Set;

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

  public void wearClothes() {
    int toWear = Worker.DAILY_CLOTHES_CONSUMPTION;
    if (clothes.size() < toWear) {
      for (Clothes c : clothes)
        c.wearOnce();
    }
    else {
      Clothes[] notWorn = Set.copyOf(this.clothes).toArray(Clothes[]::new);
      int r;
      while (toWear > 0) {
        r = RANDOM.nextInt(clothes.size());
        if (notWorn[r] != null) {
          notWorn[r].wearOnce();
          notWorn[r] = null;
          toWear--;
        }
      }
    }
  }

  public void useAllTools() {
    tools.clear();
  }
}
