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

  public ProductivityVector add(ProductivityVector other) {
    ProductivityVector res = new ProductivityVector();
    for (int i = 0; i < PRODUCT_COUNT; i++)
      res.data[i] = this.data[i] + other.data[i];
    return res;
  }

  public ProductivityVector times(int scalar) {
    ProductivityVector res = new ProductivityVector();
    for (int i = 0; i < PRODUCT_COUNT; i++)
      res.data[i] = this.data[i] * scalar;
    return res;
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
