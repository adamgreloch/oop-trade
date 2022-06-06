package pl.edu.mimuw.career;

import pl.edu.mimuw.ProductivityVector;

public class Miner extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffDiamonds(buffValue);
  }
}
