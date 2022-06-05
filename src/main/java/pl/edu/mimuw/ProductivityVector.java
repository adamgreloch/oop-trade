package pl.edu.mimuw;

import java.util.Arrays;

public class ProductivityVector {
  private final int PRODUCT_COUNT = 5;
  private int data[];

  public ProductivityVector(int food, int clothes, int tools, int diamonds, int programs) {
    this.data = new int[]{food, clothes, tools, diamonds, programs};
  }

  public ProductivityVector() {
    this.data = new int[PRODUCT_COUNT];
  }

  public ProductivityVector(int constant) {
    this.data = new int[PRODUCT_COUNT];
    Arrays.fill(this.data, constant);
  }

  public void clear() {
    this.data = new int[PRODUCT_COUNT];
  }

  public void add(ProductivityVector other) {
    for (int i = 0; i < PRODUCT_COUNT; i++)
      this.data[i] += other.data[i];
  }

  public void times(int scalar) {
    for (int i = 0; i < PRODUCT_COUNT; i++)
      this.data[i] *= scalar;
  }

  public int food() {
    return data[0];
  }

  public int clothes() {
    return data[1];
  }

  public int tools() {
    return data[2];
  }

  public int diamonds() {
    return data[3];
  }

  public int programs() {
    return data[4];
  }
}
