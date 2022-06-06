package pl.edu.mimuw;

public class MutableInteger {
  private int value;

  public MutableInteger(int value) {
    this.value = value;
  }

  public int increment() {
    return value++;
  }
}
