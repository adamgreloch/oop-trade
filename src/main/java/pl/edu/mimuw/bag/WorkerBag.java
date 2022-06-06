package pl.edu.mimuw.bag;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.agents.productivity.ProductivityBuff;
import pl.edu.mimuw.agents.productivity.ProductivityVector;
import pl.edu.mimuw.products.Clothes;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    if (this.clothes.size() <= toWear) {
      this.clothes.clear();
      return;
    }

    List<Clothes> notWorn = new LinkedList<>(this.clothes);
    Collections.shuffle(notWorn);
    for (Clothes clothes : notWorn) {
      if (toWear == 0) break;
      wearWhileCheckingCondition(clothes);
      toWear--;
    }
  }

  private void wearWhileCheckingCondition(Clothes toWear) {
    if (toWear.wearOnce() == 0) this.clothes.remove(toWear);
  }

  public void useAllTools() {
    tools.clear();
  }
}
