package pl.edu.mimuw.bag;

import pl.edu.mimuw.Simulation;
import pl.edu.mimuw.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.products.Clothes;

import java.util.Set;

public class WorkerBag extends Bag implements ProductivityBuff {
  private static final int MINOR_STARVATION_PENALTY = -100;
  private static final int MAJOR_STARVATION_PENALTY = -300;

  private final Worker owner;

  public WorkerBag(Worker owner) {
    super();
    this.owner = owner;
  }

  public ProductivityVector getBuffValue() {
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
        wearWhileCheckingCondition(c);
    }
    else {
      Clothes[] notWorn = this.clothes.toList().toArray(Clothes[]::new);
      int r;
      while (toWear > 0) {
        r = Simulation.RANDOM.nextInt(clothes.size());
        if (notWorn[r] != null) {
          wearWhileCheckingCondition(notWorn[r]);
          notWorn[r] = null;
          toWear--;
        }
      }
    }
  }

  private void wearWhileCheckingCondition(Clothes toWear) {
    if (toWear.wearOnce() == 0) this.clothes.remove(toWear);
  }

  public void useAllTools() {
    tools.clear();
  }
}
