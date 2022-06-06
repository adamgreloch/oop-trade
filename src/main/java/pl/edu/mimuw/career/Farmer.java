package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityVector;

public class Farmer extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffFood(buffValue);
  }
}
