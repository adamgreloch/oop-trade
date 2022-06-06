package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityVector;

public class Programmer extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffPrograms(buffValue);
  }
}
