package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityVector;

public class Craftsman extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffClothes(buffValue);
  }
}
