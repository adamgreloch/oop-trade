package pl.edu.mimuw.agents.career;

import pl.edu.mimuw.agents.productivity.ProductivityVector;

public class Engineer extends Occupation {
  protected ProductivityVector makeBuff(int buffValue) {
    return (new ProductivityVector()).buffTools(buffValue);
  }
}
