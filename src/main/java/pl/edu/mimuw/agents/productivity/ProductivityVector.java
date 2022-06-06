package pl.edu.mimuw.agents.productivity;

import pl.edu.mimuw.products.*;

import java.util.Arrays;

public class ProductivityVector {
  private final int PRODUCT_COUNT = 5;
  private int[] data;

  public ProductivityVector(int food, int clothes, int tools, int diamonds, int programs) {
    this.data = new int[]{food, clothes, tools, diamonds, programs};
  }

  private ProductivityVector(int[] data) {
    assert data.length == PRODUCT_COUNT;
    this.data = Arrays.copyOf(data, PRODUCT_COUNT);
  }

  public ProductivityVector() {
    this.data = new int[PRODUCT_COUNT];
  }

  public ProductivityVector(int constant) {
    this.data = new int[PRODUCT_COUNT];
    Arrays.fill(this.data, constant);
  }

  public static int find(ProductivityVector vector, Product product) {
    if (product instanceof Food) return vector.data[0];
    if (product instanceof Clothes) return vector.data[1];
    if (product instanceof Tool) return vector.data[2];
    if (product instanceof Diamond) return vector.data[3];
    if (product instanceof Program) return vector.data[4];
    throw new IllegalArgumentException("Product outside of simulation scope");
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

  private ProductivityVector buff(int idx, int buff) {
    ProductivityVector res = this.copyOf();
    res.data[idx] += buff;
    return res;
  }

  public ProductivityVector buffFood(int buff) {
    return buff(0, buff);
  }

  public ProductivityVector buffClothes(int buff) {
    return buff(1, buff);
  }

  public ProductivityVector buffTools(int buff) {
    return buff(2, buff);
  }

  public ProductivityVector buffDiamonds(int buff) {
    return buff(3, buff);
  }

  public ProductivityVector buffPrograms(int buff) {
    return buff(4, buff);
  }

  public ProductivityVector copyOf() {
    return new ProductivityVector(data);
  }
}
