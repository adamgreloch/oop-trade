package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityVector;

public class Engineer extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffTools(buffValue);
  }
}
